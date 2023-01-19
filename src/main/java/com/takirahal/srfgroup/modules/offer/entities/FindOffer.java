package com.takirahal.srfgroup.modules.offer.entities;

import com.takirahal.srfgroup.modules.offer.entities.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("FindOffer")
public class FindOffer extends Offer {

    @Column(name = "amount")
    private Double amount;

    @Column(name = "type_find_offer")
    private String typeFindOffer;
}
