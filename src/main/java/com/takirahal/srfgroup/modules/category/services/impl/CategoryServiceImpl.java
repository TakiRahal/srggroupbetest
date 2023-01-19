package com.takirahal.srfgroup.modules.category.services.impl;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.dto.filter.CategoryFilter;
import com.takirahal.srfgroup.modules.category.entities.Category;
import com.takirahal.srfgroup.modules.category.mapper.CategoryMapper;
import com.takirahal.srfgroup.modules.category.repositories.CategoryRepository;
import com.takirahal.srfgroup.modules.category.services.CategoryService;
import com.takirahal.srfgroup.modules.notification.services.impl.NotificationServiceImpl;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDTO> findByCriteria(CategoryFilter criteria, Pageable pageable) {
        return categoryRepository.findAll(createSpecification(criteria), pageable).map(categoryMapper::toDto);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(categoryMapper::toDto);
    }

    @Override
    public Boolean updateIndexCategories(List<CategoryDTO> categoryDTOList) {
        log.debug("Request to update index Categories : {}", categoryDTOList);
        categoryDTOList.stream().forEach(categoryDTO -> {
            Category category = categoryRepository.findById(categoryDTO.getId())
                    .orElseThrow(() -> new ResouorceNotFoundException("Category not found"));
            category.setIndex(categoryDTO.getIndex());
            categoryRepository.save(category);

        });
        return true;
    }

    private Specification<Category> createSpecification(CategoryFilter criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("index")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
