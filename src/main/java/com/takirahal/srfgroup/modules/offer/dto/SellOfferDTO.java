package com.takirahal.srfgroup.modules.offer.dto;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellOfferDTO extends OfferDTO {
    private Double amount;
    private Instant sellDate;
    private String typeContactClient;
    private Double shippingCost;
}
