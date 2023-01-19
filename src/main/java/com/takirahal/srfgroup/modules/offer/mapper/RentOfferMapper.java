package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface RentOfferMapper extends EntityMapper<RentOfferDTO, RentOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "email")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    RentOfferDTO toDto(RentOffer rentOffer);

    @Named("toDtoSearchOffers")
    @Mapping(target = "user", source = "user", qualifiedByName = "searchOffers")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    RentOfferDTO toDtoSearchOffers(RentOffer rentOffer);

    @Mapping(target = "offerImages", ignore = true)
    RentOffer toEntity(RentOfferDTO rentOfferDTO);

    @Named("toDtoDetailsOffer")
    @Mapping(target = "user", source = "user", qualifiedByName = "publicUser")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    RentOfferDTO toDtoDetailsOffer(RentOffer rentOffer);
}
