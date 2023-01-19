package com.takirahal.srfgroup.modules.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class JwtResponseVM implements Serializable {
    private String token;
    private String refreshToken;
}
