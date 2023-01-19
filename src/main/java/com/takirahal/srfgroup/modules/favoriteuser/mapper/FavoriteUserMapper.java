package com.takirahal.srfgroup.modules.favoriteuser.mapper;

import com.takirahal.srfgroup.modules.favoriteuser.dto.FavoriteUserDTO;
import com.takirahal.srfgroup.modules.favoriteuser.entities.FavoriteUser;
import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link FavoriteUser} and its DTO {@link FavoriteUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface FavoriteUserMapper extends EntityMapper<FavoriteUserDTO, FavoriteUser> {
    @Mapping(target = "currentUser", source = "currentUser", qualifiedByName = "publicUser")
    @Mapping(target = "favoriteUser", source = "favoriteUser", qualifiedByName = "publicUser")
    FavoriteUserDTO toDto(FavoriteUser s);
}