package com.takirahal.srfgroup.modules.rentrequest.dto;

import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestDTO implements Serializable {
    private Long id;
    private Instant sendDate;
    private String status;
    private String imageSignatureReceived;
    private RentOfferDTO rentOffer;
    private UserDTO senderUser;
    private UserDTO receiverUser;
}
