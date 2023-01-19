package com.takirahal.srfgroup.modules.monitoring.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.monitoring.dto.MetricsDto;
import com.takirahal.srfgroup.modules.monitoring.models.MeasurementsMetrics;
import com.takirahal.srfgroup.modules.monitoring.models.Metrics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface MetricsMapper  extends EntityMapper<MetricsDto, Metrics> {

    @Named("measurementsValue")
    default String measurementsToValue(MeasurementsMetrics[] measurements) {
        return measurements[0].getValue();
    }

    @Mapping(target = "value", source = "measurements", qualifiedByName = "measurementsValue")
    MetricsDto toDto(Metrics metrics);


}
