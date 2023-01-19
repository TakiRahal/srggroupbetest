package com.takirahal.srfgroup.modules.monitoring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementsMetrics {
    private String statistic;
    private String value;
}
