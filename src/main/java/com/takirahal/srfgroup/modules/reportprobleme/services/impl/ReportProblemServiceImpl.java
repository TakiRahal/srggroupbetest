package com.takirahal.srfgroup.modules.reportprobleme.services.impl;

import com.takirahal.srfgroup.modules.reportprobleme.dto.filter.ReportProblemFilter;
import com.takirahal.srfgroup.modules.reportprobleme.entities.ReportProbleme;
import com.takirahal.srfgroup.modules.reportprobleme.repositories.ReportProblemeRepository;
import com.takirahal.srfgroup.modules.reportprobleme.services.ReportProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportProblemServiceImpl implements ReportProblemService {

    @Autowired
    ReportProblemeRepository reportProblemeRepository;

    @Override
    public Page<ReportProbleme> findByCriteria(ReportProblemFilter criteria, Pageable pageable) {
        return reportProblemeRepository.findAll(pageable);
    }
}
