package com.takirahal.srfgroup.modules.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferWithMyFavoriteUserDTO {
    OfferDTO offer;
    boolean isMyFavoriteUser;
}
