package com.takirahal.srfgroup.modules.offer.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.offer.dto.OfferImagesDTO;
import com.takirahal.srfgroup.modules.offer.entities.OfferImages;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = { OfferMapper.class, UserMapper.class })
public interface OfferImagesMapper extends EntityMapper<OfferImagesDTO, OfferImages> {

    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    OfferImagesDTO toDto(OfferImages s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<OfferImagesDTO> toDtoIdSet(Set<OfferImages> offerImages);
}
