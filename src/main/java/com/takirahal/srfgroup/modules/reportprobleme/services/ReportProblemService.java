package com.takirahal.srfgroup.modules.reportprobleme.services;

import com.takirahal.srfgroup.modules.reportprobleme.dto.filter.ReportProblemFilter;
import com.takirahal.srfgroup.modules.reportprobleme.entities.ReportProbleme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportProblemService {
    Page<ReportProbleme> findByCriteria(ReportProblemFilter criteria, Pageable pageable);
}
