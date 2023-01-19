package com.takirahal.srfgroup.modules.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsDto {
    private String name;
    private String description;
    private String value;
}
