package com.takirahal.srfgroup.modules.offer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentOfferDTO extends OfferDTO {
    private Double amount;
    private Instant startDate;
    private Instant endDate;
    private String typePeriodRent;
}
