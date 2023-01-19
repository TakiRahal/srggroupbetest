package com.takirahal.srfgroup.modules.cart.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsCartGlobal{
    private int numberOfProducts; // number of products selected
    private Double totalCarts; // total all carts withou tax
    private Double shippingCost; // Shipping delivery cost
}
