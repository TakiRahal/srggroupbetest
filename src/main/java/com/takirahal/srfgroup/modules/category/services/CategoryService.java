package com.takirahal.srfgroup.modules.category.services;

import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.category.dto.filter.CategoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    /**
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Page<CategoryDTO> findByCriteria(CategoryFilter criteria, Pageable pageable);

    /**
     *
     * @param categoryDTO
     * @return
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     *
     * @param id
     * @return
     */
    Optional<CategoryDTO> findOne(Long id);

    /**
     *
     * @param categoryDTOList
     * @return
     */
    Boolean updateIndexCategories(List<CategoryDTO> categoryDTOList);
}
