package com.takirahal.srfgroup.modules.chat.mapper;

import com.takirahal.srfgroup.modules.chat.dto.MessageDTO;
import com.takirahal.srfgroup.modules.chat.entities.Message;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ConversationMapper.class })
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "senderUser", source = "senderUser", qualifiedByName = "message")
    @Mapping(target = "receiverUser", source = "receiverUser", qualifiedByName = "message")
    @Mapping(target = "conversation", source = "conversation", qualifiedByName = "id")
    MessageDTO toDto(Message s);
}
