package com.takirahal.srfgroup.modules.cart.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorOrder")
    @SequenceGenerator(name = "sequenceGeneratorOrder", sequenceName = "sequence_name_order", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "number_of_products")
    private int numberOfProducts; // number of products selected

    @Column(name = "total_carts")
    private Double totalCarts; // total all carts withou tax

    @Column(name = "tax_delivery")
    private Double taxDelivery; // Tax for delivery

    @Column(name = "total_globalCarts")
    private Double totalGlobalCarts; // For total global: total all carts + tax

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "passed_date")
    private Instant passedDate;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "sg_order_cart",
            joinColumns = { @JoinColumn(name = "order_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "cart_id", referencedColumnName = "id") }
    )
    private Set<Cart> carts = new HashSet<>();

    @ManyToOne
    private User user;
}
