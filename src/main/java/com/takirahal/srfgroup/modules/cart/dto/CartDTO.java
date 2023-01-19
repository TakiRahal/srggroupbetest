package com.takirahal.srfgroup.modules.cart.dto;

import com.takirahal.srfgroup.modules.cart.entities.Order;
import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO implements Serializable {

    private Long id;
    private int quantity;
    private Double total;
    private String status;
    private Instant passedDate;
    private SellOfferDTO sellOffer;
    private UserDTO user;
    // private Set<OrderDTO> orders;
}
