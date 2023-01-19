package com.takirahal.srfgroup.modules.permission.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.permission.dto.PermissionDTO;
import com.takirahal.srfgroup.modules.permission.entities.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PermissionMapper  extends EntityMapper<PermissionDTO, Permission> {
}
