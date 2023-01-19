package com.takirahal.srfgroup.modules.offer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.takirahal.srfgroup.modules.address.dto.AddressDTO;
import com.takirahal.srfgroup.modules.category.dto.CategoryDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.utils.LocalDateDeserializer;
import com.takirahal.srfgroup.utils.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO  implements Serializable {
    private Long id;

    private String title;

    @Lob
    private String description;

    private Instant dateCreated;

    private String typeOffer;

    private Boolean available = true;

    private Set<OfferImagesDTO> offerImages = new HashSet<>();

    private UserDTO user;

    private AddressDTO address;

    private CategoryDTO category;

    private Boolean blockedByReported;
}
