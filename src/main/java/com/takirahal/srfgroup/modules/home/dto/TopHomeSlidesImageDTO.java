package com.takirahal.srfgroup.modules.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopHomeSlidesImageDTO  implements Serializable {
    private Long id;
    @Lob
    private String descriptionAr;
    @Lob
    private String descriptionFr;
    @Lob
    private String descriptionEn;
    private String imageDesktop;
    private String imageMobile;
}
