package com.takirahal.srfgroup.modules.chat.dto;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private Long id;

    @Lob
    private String content;

    private Instant dateCreated;

    private Boolean isRead;

    private UserDTO senderUser;

    private UserDTO receiverUser;

    private ConversationDTO conversation;
}
