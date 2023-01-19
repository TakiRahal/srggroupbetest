package com.takirahal.srfgroup.modules.cart.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsCarts {
    // private int numberOfProducts; // number of products selected
    // private Double totalCarts; // total all carts withou tax
    // private Double taxDelivery; // Tax for delivery
    private List<DetailsCartGlobal> detailsCartGlobal;
    private Double totalGlobalCarts; // For total global: total all carts + tax

}
