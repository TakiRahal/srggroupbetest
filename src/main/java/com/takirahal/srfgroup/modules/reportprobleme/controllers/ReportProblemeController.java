package com.takirahal.srfgroup.modules.reportprobleme.controllers;

import com.takirahal.srfgroup.modules.reportprobleme.dto.filter.ReportProblemFilter;
import com.takirahal.srfgroup.modules.reportprobleme.entities.ReportProbleme;
import com.takirahal.srfgroup.modules.reportprobleme.repositories.ReportProblemeRepository;
import com.takirahal.srfgroup.modules.reportprobleme.services.ReportProblemService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.reportprobleme.entities.ReportProbleme}.
 */
@RestController
@RequestMapping("/api/report-probleme/")
public class ReportProblemeController {

    private final Logger log = LoggerFactory.getLogger(ReportProblemeController.class);

    @Autowired
    ReportProblemeRepository reportProblemeRepository;

    @Autowired
    ReportProblemService reportProblemService;

    @PostMapping("create")
    public ResponseEntity<ReportProbleme> create(@RequestBody ReportProbleme reportProbleme) {
        log.info("REST request to save ReportProbleme : {}", reportProbleme);
        ReportProbleme result = reportProblemeRepository.save(reportProbleme);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("report_probleme.report_probleme_added_succefully", result.getId().toString()), HttpStatus.CREATED);
    }


    @GetMapping("admin")
    public ResponseEntity<Page<ReportProbleme>> getAllReportProblems(ReportProblemFilter criteria, Pageable pageable) {
        log.info("REST request to get Report Problem by criteria: {}", criteria);
        Page<ReportProbleme> page = reportProblemService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
