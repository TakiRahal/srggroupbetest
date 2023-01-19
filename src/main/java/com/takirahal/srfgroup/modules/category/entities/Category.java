package com.takirahal.srfgroup.modules.category.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorCategory", sequenceName = "sequence_name_category", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorCategory")
    private Long id;

    @Column(name = "title_ar")
    private String titleAr;

    @Column(name = "title_fr")
    private String titleFr;

    @Column(name = "title_en")
    private String titleEn;

    @Lob
    // // @Type(type = "org.hibernate.type.TextType")
    @Column(name = "image_content")
    private String imageContent;

    @Column(name = "index")
    private Integer index;
}
