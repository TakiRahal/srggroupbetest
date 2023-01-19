package com.takirahal.srfgroup.modules.offer.entities;

import com.takirahal.srfgroup.modules.offer.entities.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SellOffer")
public class SellOffer extends Offer implements Serializable {

    @Column(name = "amount")
    private Double amount;

    @Column(name = "sell_date")
    private Instant sellDate;

    @Column(name = "type_contact_client")
    private String typeContactClient;

    @Column(name = "shipping_cost")
    private Double shippingCost;
}
