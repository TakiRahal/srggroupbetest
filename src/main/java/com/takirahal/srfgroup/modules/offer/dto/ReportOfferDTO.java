package com.takirahal.srfgroup.modules.offer.dto;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportOfferDTO implements Serializable {
    private Long id;
    private OfferDTO offer;
    private UserDTO user;
}
