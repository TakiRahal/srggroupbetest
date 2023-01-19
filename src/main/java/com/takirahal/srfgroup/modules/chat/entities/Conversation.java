package com.takirahal.srfgroup.modules.chat.entities;

import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_conversation")
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorConversation", sequenceName = "sequence_name_conversation", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorConversation")
    private Long id;

    @Column(name = "date_created")
    private Instant dateCreated;

    @ManyToOne
    private User senderUser;

    @ManyToOne
    private User receiverUser;
}
