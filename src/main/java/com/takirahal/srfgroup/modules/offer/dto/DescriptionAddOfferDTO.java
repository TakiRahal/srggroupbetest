package com.takirahal.srfgroup.modules.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;

/**
 * A DTO for the {@link com.takirahal.srfgroup.modules.offer.dto.DescriptionAddOfferDTO} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionAddOfferDTO implements Serializable {
    private Long id;

    @Lob
    private String descriptionAr;

    @Lob
    private String descriptionFr;

    @Lob
    private String descriptionEn;
}
