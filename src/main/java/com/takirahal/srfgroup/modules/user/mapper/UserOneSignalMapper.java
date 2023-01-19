package com.takirahal.srfgroup.modules.user.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.dto.UserOneSignalDTO;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserOneSignalMapper extends EntityMapper<UserOneSignalDTO, UserOneSignal> {
}
