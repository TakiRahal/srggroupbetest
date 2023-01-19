package com.takirahal.srfgroup.modules.chat.repositories;

import com.takirahal.srfgroup.modules.chat.entities.Conversation;
import com.takirahal.srfgroup.modules.chat.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    Optional<Message> findFirstByConversationOrderByIdDesc(Conversation conversation);


    @Query(
            "SELECT COUNT(message) FROM Message message WHERE message.receiverUser.id = :userId  AND message.isRead=false"
    )
    long getNumberNotSeeMessagesForUserId(@Param("userId") Long userId);


    @Modifying
    @Query("DELETE from Message m where m.conversation.id=:id")
    void deleteMessagesByConversationId(@Param("id") Long id);
}
