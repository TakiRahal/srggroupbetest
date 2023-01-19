package com.takirahal.srfgroup.modules.permission.dto;

import com.takirahal.srfgroup.modules.user.enums.EAuthority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserAuthorityDTO {
    private Long id;
    private EAuthority nameAuthority;
}
