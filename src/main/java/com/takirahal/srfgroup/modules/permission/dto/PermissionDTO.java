package com.takirahal.srfgroup.modules.permission.dto;

import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PermissionDTO implements Serializable {
    private Long id;
    private EPermission name;
    private String pathApi;
    private String description;
}
