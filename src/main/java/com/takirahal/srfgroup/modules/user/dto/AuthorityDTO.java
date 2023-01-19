package com.takirahal.srfgroup.modules.user.dto;

import com.takirahal.srfgroup.modules.permission.dto.PermissionDTO;
import com.takirahal.srfgroup.modules.permission.entities.Permission;
import com.takirahal.srfgroup.modules.user.enums.EAuthority;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AuthorityDTO implements Serializable {
    private Integer id;
    private EAuthority name;
    private Set<PermissionDTO> permissions = new HashSet<>();
}
