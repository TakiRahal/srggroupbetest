package com.takirahal.srfgroup.modules.chat.dto.Filter;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationFilter {
    private UserDTO senderUser;
    private UserDTO receiverUser;
}
