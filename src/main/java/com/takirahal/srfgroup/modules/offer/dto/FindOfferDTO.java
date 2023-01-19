package com.takirahal.srfgroup.modules.offer.dto;

import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOfferDTO extends OfferDTO {
    private Double amount;
    private String typeFindOffer;
}
