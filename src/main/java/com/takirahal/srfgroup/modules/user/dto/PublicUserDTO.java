package com.takirahal.srfgroup.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicUserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String imageUrl;

    private String phone;

    private String sourceRegister;

    private String idOneSignal;

    private Set<String> authorities;
}
