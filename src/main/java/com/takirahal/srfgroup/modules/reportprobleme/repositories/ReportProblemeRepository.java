package com.takirahal.srfgroup.modules.reportprobleme.repositories;

import com.takirahal.srfgroup.modules.reportprobleme.dto.filter.ReportProblemFilter;
import com.takirahal.srfgroup.modules.reportprobleme.entities.ReportProbleme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportProblemeRepository extends JpaRepository<ReportProbleme, Long> {
}
