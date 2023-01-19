package com.takirahal.srfgroup.modules.offer.dto.filter;

import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferFilter {
    private Long id;

    private String title;

    @Lob
    private String description;

    private Instant dateCreated;

    private String typeOffer;

    private UserOfferFilter user;

    private AddressDTO address;

    private CategoryDTO category;

    private Boolean blockedByReported = false;
}
