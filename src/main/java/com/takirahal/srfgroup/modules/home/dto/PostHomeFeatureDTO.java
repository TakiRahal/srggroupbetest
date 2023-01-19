package com.takirahal.srfgroup.modules.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostHomeFeatureDTO implements Serializable {
    private Long id;

    @Lob
    private String descriptionAr;

    @Lob
    private String descriptionFr;

    @Lob
    private String descriptionEn;

    @Lob
    private String image;
}
