package com.takirahal.srfgroup.modules.user.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOfferFilter {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;
}
