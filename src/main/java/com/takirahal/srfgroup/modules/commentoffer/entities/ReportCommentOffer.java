package com.takirahal.srfgroup.modules.commentoffer.entities;

import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_report_comment_offer")
public class ReportCommentOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorReportCommentOffer", sequenceName = "sequence_name_report_comment_offer", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorReportCommentOffer")
    private Long id;

    @ManyToOne
    private CommentOffer commentOffer;

    @ManyToOne
    private User user;
}
