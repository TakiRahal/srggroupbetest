package com.takirahal.srfgroup.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileFavoriteUserDTO implements Serializable {
    private UserDTO user;
    private boolean isMyFavoriteUser;
}
