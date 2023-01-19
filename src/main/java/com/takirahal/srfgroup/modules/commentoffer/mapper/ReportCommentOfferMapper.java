package com.takirahal.srfgroup.modules.commentoffer.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.commentoffer.dto.ReportCommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.entities.ReportCommentOffer;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CommentOfferMapper.class, UserMapper.class })
public interface ReportCommentOfferMapper extends EntityMapper<ReportCommentOfferDTO, ReportCommentOffer> {

    @Mapping(target = "commentOffer", source = "commentOffer", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "email")
    ReportCommentOfferDTO toDto(ReportCommentOffer s);
}
