package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.FindOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface FindOfferMapper extends EntityMapper<FindOfferDTO, FindOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "email")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    FindOfferDTO toDto(FindOffer findOffer);

    @Named("toDtoSearchOffers")
    @Mapping(target = "user", source = "user", qualifiedByName = "searchOffers")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    FindOfferDTO toDtoSearchOffers(FindOffer findOffer);

    @Mapping(target = "offerImages", ignore = true)
    FindOffer toEntity(FindOfferDTO findOfferDTO);

    @Named("toDtoDetailsOffer")
    @Mapping(target = "user", source = "user", qualifiedByName = "publicUser")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    FindOfferDTO toDtoDetailsOffer(FindOffer findOffer);
}
