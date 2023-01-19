package com.takirahal.srfgroup.modules.chat.mapper;

import com.takirahal.srfgroup.modules.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.modules.chat.entities.Conversation;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ConversationMapper extends EntityMapper<ConversationDTO, Conversation> {
    @Mapping(target = "senderUser", source = "senderUser", qualifiedByName = "conversation")
    @Mapping(target = "receiverUser", source = "receiverUser", qualifiedByName = "conversation")
    ConversationDTO toDto(Conversation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConversationDTO toDtoId(Conversation conversation);
}
