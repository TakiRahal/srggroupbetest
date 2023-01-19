package com.takirahal.srfgroup.modules.chat.repositories;

import com.takirahal.srfgroup.modules.chat.entities.Conversation;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>, JpaSpecificationExecutor<Conversation> {

    Optional<Conversation> findBySenderUserAndReceiverUser(User userSender, User receiverUser);
    Optional<Conversation> findByReceiverUserAndSenderUser(User userSender, User receiverUser);
}
