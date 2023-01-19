package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.offer.dto.AdvertisingPerPeriodDTO;
import com.takirahal.srfgroup.modules.offer.entities.AdvertisingPerPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OfferMapper.class})
public interface AdvertisingPerPeriodMapper extends EntityMapper<AdvertisingPerPeriodDTO, AdvertisingPerPeriod> {

    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    AdvertisingPerPeriodDTO toDto(AdvertisingPerPeriod advertisingPerPeriod);
}
