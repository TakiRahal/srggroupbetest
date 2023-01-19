package com.takirahal.srfgroup.modules.faq.services.impl;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import com.takirahal.srfgroup.modules.faq.mapper.FaqMapper;
import com.takirahal.srfgroup.modules.faq.repositories.FaqRepository;
import com.takirahal.srfgroup.modules.faq.services.FaqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    private final Logger log = LoggerFactory.getLogger(FaqServiceImpl.class);

    @Autowired
    FaqRepository faqRepository;

    @Autowired
    FaqMapper faqMapper;

    @Override
    public FaqDTO save(FaqDTO faqDTO) {
        log.debug("Request to save Faq : {}", faqDTO);
        Faq faq = faqMapper.toEntity(faqDTO);
        faq = faqRepository.save(faq);
        return faqMapper.toDto(faq);
    }

    @Override
    // @Cacheable(value="faqs")
    public Page<FaqDTO> findByCriteria(FaqFilter criteria, Pageable pageable) {
        log.debug("Request to get Faq by : {}, : {}", criteria, pageable);
        return faqRepository.findAll(createSpecification(criteria), pageable).map(item-> faqMapper.toDto(item));
    }

    @Override
    public Page<Faq> findByCriteriaEntity(FaqFilter criteria, Pageable pageable) {
        return faqRepository.findAll(createSpecification(criteria), pageable);
    }

    @Override
    public List<Faq> findAll() {
        return faqRepository.findAll();
    }

    protected Specification<Faq> createSpecification(FaqFilter faqFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
