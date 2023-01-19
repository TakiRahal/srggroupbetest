package com.takirahal.srfgroup.modules.offer.dto;

import com.takirahal.srfgroup.modules.offer.enums.ModulePubOffer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
public class AdvertisingPerPeriodDTO implements Serializable {
    private Long id;
    private Instant startDate;
    private Instant endDate;
    private boolean available;
    private ModulePubOffer module;
    private OfferDTO offer;
}
