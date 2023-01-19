package com.takirahal.srfgroup.modules.faq.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Types;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_faq")
public class Faq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorFaq", sequenceName = "sequence_name_faq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorFaq")
    private Long id;

    @Column(name = "question_ar")
    private String questionAr;

    @Column(name = "question_fr")
    private String questionFr;

    @Column(name = "question_en")
    private String questionEn;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "response_ar", columnDefinition="TEXT")
    private String responseAr;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "response_fr", columnDefinition="TEXT")
    private String responseFr;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "response_en", columnDefinition="TEXT")
    private String responseEn;

}
