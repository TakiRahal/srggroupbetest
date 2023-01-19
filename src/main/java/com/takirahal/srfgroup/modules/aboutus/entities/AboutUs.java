package com.takirahal.srfgroup.modules.aboutus.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Types;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_about_us")
public class AboutUs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorAboutUs", sequenceName = "sequence_name_about_us", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorAboutUs")
    private Long id;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "content_ar", columnDefinition="TEXT")
    private String contentAr;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "content_en", columnDefinition="TEXT")
    private String contentEn;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "content_fr", columnDefinition="TEXT")
    private String contentFr;
}
