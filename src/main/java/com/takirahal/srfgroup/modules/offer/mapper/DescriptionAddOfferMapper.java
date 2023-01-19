package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.offer.dto.DescriptionAddOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.DescriptionAddOffer;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link DescriptionAddOffer} and its DTO {@link DescriptionAddOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DescriptionAddOfferMapper extends EntityMapper<DescriptionAddOfferDTO, DescriptionAddOffer> {}
