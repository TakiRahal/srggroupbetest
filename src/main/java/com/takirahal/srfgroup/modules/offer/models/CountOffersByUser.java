package com.takirahal.srfgroup.modules.offer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountOffersByUser {
    private Long countSellOffers;
    private Long countRentOffers;
    private Long countFindOffers;
}
