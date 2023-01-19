package com.takirahal.srfgroup.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TokenRefreshRequestDTO implements Serializable {
    @NotBlank
    private String refreshToken;
}
