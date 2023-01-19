package com.takirahal.srfgroup.modules.monitoring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metrics {
    private String name;
    private String description;
    private MeasurementsMetrics[] measurements;
}
