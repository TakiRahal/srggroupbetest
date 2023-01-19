package com.takirahal.srfgroup.modules.chat.services.impl;

import com.takirahal.srfgroup.modules.chat.dto.MessageDTO;
import com.takirahal.srfgroup.modules.chat.entities.Message;
import com.takirahal.srfgroup.modules.chat.mapper.MessageMapper;
import com.takirahal.srfgroup.modules.chat.repositories.MessageRepository;
import com.takirahal.srfgroup.modules.chat.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save Message : {}", messageDTO);
        Message message = messageMapper.toEntity(messageDTO);
        message = messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public Page<MessageDTO> findByCriteria(Pageable pageable, Long conversationId) {
        log.debug("find message by criteria : {}, page: {}", pageable);
        Page<MessageDTO> messageDTOS = messageRepository.findAll(createSpecification(conversationId), pageable).map(messageMapper::toDto);
        messageDTOS.stream().forEach(msg -> {
            if(msg.getIsRead().equals(Boolean.FALSE)){
                msg.setIsRead(Boolean.TRUE);
                save(msg);
            }
        });
        return messageDTOS;
    }

    private Specification<Message> createSpecification(Long conversationId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("conversation").get("id"), conversationId));

            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
