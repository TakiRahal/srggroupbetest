package com.takirahal.srfgroup.modules.monitoring.services;

import com.takirahal.srfgroup.modules.monitoring.dto.MetricsDto;

import java.util.List;

public interface MetricsService {
    List<MetricsDto> getAllMetrics();
}
