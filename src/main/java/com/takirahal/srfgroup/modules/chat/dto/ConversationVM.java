package com.takirahal.srfgroup.modules.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationVM implements Serializable {

    ConversationDTO conversation;

    @Lob
    private String content;

    public ConversationDTO getConversation() {
        return conversation;
    }
}
