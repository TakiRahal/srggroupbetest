package com.takirahal.srfgroup.modules.chat.dto;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO implements Serializable {
    private Long id;

    private Instant dateCreated;

    private UserDTO senderUser;

    private UserDTO receiverUser;
}
