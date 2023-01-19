package com.takirahal.srfgroup.modules.cart.dto;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable{
    private Long id;
    private int numberOfProducts;
    private Double totalCarts;
    // private Double taxDelivery;
    // private Double totalGlobalCarts;
    private String paymentMode;
    private Instant passedDate;
    private String status;
    private Set<CartDTO> carts = new HashSet<>();
    private UserDTO user;
}
