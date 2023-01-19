package com.takirahal.srfgroup.modules.rentrequest.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.offer.mapper.RentOfferMapper;
import com.takirahal.srfgroup.modules.rentrequest.dto.RentRequestDTO;
import com.takirahal.srfgroup.modules.rentrequest.entities.RentRequest;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RentOfferMapper.class})
public interface RentRequestMapper  extends EntityMapper<RentRequestDTO, RentRequest> {

    @Mapping(target = "senderUser", source = "senderUser", qualifiedByName = "email")
    @Mapping(target = "receiverUser", source = "receiverUser", qualifiedByName = "email")
    @Mapping(target = "rentOffer", source = "rentOffer", qualifiedByName = "toDtoDetailsOffer")
    RentRequestDTO toDto(RentRequest entity);
}
