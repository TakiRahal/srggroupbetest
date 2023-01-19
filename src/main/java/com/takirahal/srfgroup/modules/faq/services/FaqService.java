package com.takirahal.srfgroup.modules.faq.services;

import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.entities.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FaqService {
    FaqDTO save(FaqDTO faqDTO);

    Page<FaqDTO> findByCriteria(FaqFilter criteria, Pageable pageable);

    Page<Faq> findByCriteriaEntity(FaqFilter criteria, Pageable pageable);

    List<Faq> findAll();
}
