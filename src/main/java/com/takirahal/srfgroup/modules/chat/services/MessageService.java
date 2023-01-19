package com.takirahal.srfgroup.modules.chat.services;

import com.takirahal.srfgroup.modules.chat.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    /**
     * Save a message.
     *
     * @param messageDTO the entity to save.
     * @return the persisted entity.
     */
    MessageDTO save(MessageDTO messageDTO);

    /**
     *
     * @param pageable
     * @param conversationId
     * @return
     */
    Page<MessageDTO> findByCriteria(Pageable pageable, Long conversationId);
}
