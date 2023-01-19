package com.takirahal.srfgroup.modules.chat.controllers;

import com.takirahal.srfgroup.modules.chat.dto.ConversationDTO;
import com.takirahal.srfgroup.modules.chat.dto.ConversationVM;
import com.takirahal.srfgroup.modules.chat.dto.ConversationWithLastMessageDTO;
import com.takirahal.srfgroup.modules.chat.dto.Filter.ConversationFilter;
import com.takirahal.srfgroup.modules.chat.services.ConversationService;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/conversation/")
public class ConversationController {

    private final Logger log = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    ConversationService conversationService;

    /**
     * {@code POST  /conversations} : Create a new conversation.
     *
     * @param conversationVM the conversationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conversationDTO, or with status {@code 400 (Bad Request)} if the conversation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create/message")
    public ResponseEntity<Boolean> createConversationMessage(@RequestBody ConversationVM conversationVM) throws URISyntaxException {
        log.info("REST request to save Conversation : {}", conversationVM);
        if (conversationVM.getConversation().getId() != null) {
            throw new BadRequestAlertException("A new conversation cannot already have an ID idexists");
        }
        conversationService.createConversationMessage(conversationVM);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("message.sent_succefully", ""), HttpStatus.CREATED);
    }

    /**
     *
     * @param conversationFilter
     * @param pageable
     * @return
     */
    @GetMapping("current-user")
    public ResponseEntity<Page<ConversationWithLastMessageDTO>> getAllConversationsCurrentUser(
            ConversationFilter conversationFilter,
            Pageable pageable
    ) {
        log.info("REST request to get Conversations by criteria: {}", conversationFilter);
        Page<ConversationWithLastMessageDTO> page = conversationService.getOffersByCurrentUser(conversationFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * {@code DELETE  /comment-offers/:id} : delete the "id" commentOffer.
     *
     * @param id the id of the commentOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteConversation(@PathVariable Long id) {
        log.info("REST request to delete conversation : {}", id);
        conversationService.delete(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("chat.conversation_delete_succefully", id.toString()), HttpStatus.OK);
    }

}
