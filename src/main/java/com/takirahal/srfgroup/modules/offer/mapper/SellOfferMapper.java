package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OfferImagesMapper.class })
public interface SellOfferMapper extends EntityMapper<SellOfferDTO, SellOffer> {

    @Mapping(target = "user", source = "user", qualifiedByName = "email")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    SellOfferDTO toDto(SellOffer sellOffer);

    @Named("toDtoSearchOffers")
    @Mapping(target = "user", source = "user", qualifiedByName = "searchOffers")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    SellOfferDTO toDtoSearchOffers(SellOffer sellOffer);

    @Mapping(target = "offerImages", ignore = true)
    SellOffer toEntity(SellOfferDTO sellOfferDTO);

    @Named("toDtoDetailsOffer")
    @Mapping(target = "user", source = "user", qualifiedByName = "publicUser")
    @Mapping(target = "offerImages", source = "offerImages", qualifiedByName = "idSet")
    SellOfferDTO toDtoDetailsOffer(SellOffer sellOffer);
}
