package com.takirahal.srfgroup.modules.user.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.dto.RefreshTokenDTO;
import com.takirahal.srfgroup.modules.user.entities.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface RefreshTokenMapper extends EntityMapper<RefreshTokenDTO, RefreshToken> {
}
