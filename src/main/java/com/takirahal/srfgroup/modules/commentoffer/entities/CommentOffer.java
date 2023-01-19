package com.takirahal.srfgroup.modules.commentoffer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takirahal.srfgroup.modules.offer.entities.Offer;
import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_comment_offer")
public class CommentOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorCommentOffer", sequenceName = "sequence_name_comment_offer", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorCommentOffer")
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Lob
    // @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @Column(name = "blocked_by_reported", nullable = false)
    private boolean blockedByReported = false;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "offerImages" }, allowSetters = true)
    private Offer offer;

    @ManyToOne
    private User user;
}
