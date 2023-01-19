package com.takirahal.srfgroup.modules.commentoffer.mapper;

import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.offer.mapper.OfferMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CommentOffer} and its DTO {@link CommentOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = { OfferMapper.class, UserMapper.class })
public interface CommentOfferMapper extends EntityMapper<CommentOfferDTO, CommentOffer> {
    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "commentOffer")
    CommentOfferDTO toDto(CommentOffer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommentOfferDTO toDtoId(CommentOffer commentOffer);
}