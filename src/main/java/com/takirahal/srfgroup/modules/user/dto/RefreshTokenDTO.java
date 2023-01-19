package com.takirahal.srfgroup.modules.user.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class RefreshTokenDTO implements Serializable {
    private long id;
    private UserDTO user;
    private String token;
    private Instant expiryDate;
}
