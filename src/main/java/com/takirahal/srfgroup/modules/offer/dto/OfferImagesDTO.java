package com.takirahal.srfgroup.modules.offer.dto;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferImagesDTO  implements Serializable {
    private Long id;

    private String path;

    private Instant dateCreated;

    private OfferDTO offer;

    // private UserDTO user;
}
