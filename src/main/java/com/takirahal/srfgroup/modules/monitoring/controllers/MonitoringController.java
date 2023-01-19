package com.takirahal.srfgroup.modules.monitoring.controllers;

import com.takirahal.srfgroup.modules.monitoring.dto.MetricsDto;
import com.takirahal.srfgroup.modules.monitoring.services.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monitoring/admin/")
public class MonitoringController {

    private final Logger log = LoggerFactory.getLogger(MonitoringController.class);

    @Autowired
    MetricsService metricsService;

    @GetMapping("metrics")
    public ResponseEntity<List<MetricsDto>> getAllMetricsData() {
        log.info("REST request to get list of Metrics : ", "");
        List<MetricsDto> result = metricsService.getAllMetrics();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
