package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.offer.dto.ReportOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.ReportOffer;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ReportOffer} and its DTO {@link ReportOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = { OfferMapper.class, UserMapper.class })
public interface ReportOfferMapper  extends EntityMapper<ReportOfferDTO, ReportOffer> {

    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "email")
    ReportOfferDTO toDto(ReportOffer s);
}
