package com.takirahal.srfgroup.modules.user.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Named("currentUserPrincipal")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    @Mapping(target = "authorities", source = "authorities")
    UserDTO toCurrentUserPrincipal(UserPrincipal user);

    @Named("currentUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    @Mapping(target = "authorities", source = "authorities")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "langKey", source = "langKey")
    @Mapping(target = "linkProfileFacebook", source = "linkProfileFacebook")
    UserDTO toCurrentUser(User user);

    @Named("currentUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "idName")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "registerDate", source = "registerDate")
    @Mapping(target = "blocked", source = "blocked")
    @Mapping(target = "activatedAccount", source = "activatedAccount")
    UserDTO toDtoListAdmin(User user);

    @Named("email")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    UserDTO toDtoIdEmail(User user);

    @Named("currentUserToEntity")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    User currentUserToEntity(UserPrincipal user);

    @Named("publicUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "registerDate", source = "registerDate")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    @Mapping(target = "authorities", source = "authorities")
    @Mapping(target = "linkProfileFacebook", source = "linkProfileFacebook")
    @Mapping(target = "address", source = "address")
    UserDTO toDtoPublicUser(User user);

    @Named("searchOffers")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    UserDTO toDtoSearchOffers(User user);

    @Named("commentOffer")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    UserDTO toDtoCommentOffer(User user);

    @Named("conversation")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    UserDTO toDtoConversation(User user);

    @Named("message")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "sourceConnectedDevice", source = "sourceConnectedDevice")
    UserDTO toDtoMessage(User user);
}
