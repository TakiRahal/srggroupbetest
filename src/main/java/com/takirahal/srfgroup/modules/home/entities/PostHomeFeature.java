package com.takirahal.srfgroup.modules.home.entities;

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
@Table(name = "sg_post_home_feature")
public class PostHomeFeature  implements Serializable {

    @Id
    @SequenceGenerator(name = "sequenceGeneratorPostHomeFeature", sequenceName = "sequence_name_post_home_feature", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorPostHomeFeature")
    private Long id;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "descriptionAr", columnDefinition="TEXT")
    private String descriptionAr;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "descriptionFr", columnDefinition="TEXT")
    private String descriptionFr;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "descriptionEn", columnDefinition="TEXT")
    private String descriptionEn;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "image", columnDefinition="TEXT")
    private String image;
}
