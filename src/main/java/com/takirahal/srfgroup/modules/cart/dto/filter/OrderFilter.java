package com.takirahal.srfgroup.modules.cart.dto.filter;

import com.takirahal.srfgroup.modules.user.dto.filter.UserOfferFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilter {
    private Long id;
    private String status;
    UserOfferFilter user;
}
