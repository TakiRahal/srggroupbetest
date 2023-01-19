package com.takirahal.srfgroup.modules.rentrequest.dto.filter;

import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.dto.filter.UserFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestFilter {
    private Long id;
    private Instant sendDate;
    private String status;
    private RentOfferDTO rentOffer;
    private UserFilter senderUser;
    private UserFilter receiverUser;
}
