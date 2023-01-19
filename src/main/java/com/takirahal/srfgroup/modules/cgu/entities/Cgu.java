package com.takirahal.srfgroup.modules.cgu.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Types;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_cgu")
public class Cgu implements Serializable {

    @Id
    @SequenceGenerator(name = "sequenceGeneratorCgu", sequenceName = "sequence_name_cgu", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorCgu")
    private Long id;

    @UpdateTimestamp
    @Column(name = "modify_date")
    private Instant modifyDate;

    @Lob
    // @Type(type = "org.hibernate.type.TextType")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "content_ar", columnDefinition="TEXT")
    private String contentAr;

    @Lob
    // @Type(type = "org.hibernate.type.TextType")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "content_fr", columnDefinition="TEXT")
    private String contentFr;

    @Lob
    // @Type(type = "org.hibernate.type.TextType")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "content_en", columnDefinition="TEXT")
    private String contentEn;
}
