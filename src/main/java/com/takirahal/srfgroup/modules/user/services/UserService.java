package com.takirahal.srfgroup.modules.user.services;

import com.takirahal.srfgroup.modules.user.dto.*;
import com.takirahal.srfgroup.modules.user.dto.filter.UserFilter;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.websocket.models.ConnectedUser;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    User registerUser(RegisterDTO registerDTO);

    Optional<User> activateRegistration(String key);

    UserDTO findById(Long id);

    UserDTO findByIdForAdmin(Long id);

    UserDTO getCurrentUser();

    JwtResponseVM signinClient(LoginDTO loginDTO);

    String signInAdmin(LoginDTO loginDTO);

    UserDTO updateAvatar(MultipartFile file);

    Resource getAvatar(Long id, String filename);

    UserDTO updateCurrentUser(UserDTO user);

    Boolean updatePasswordCurrentUser(UpdatePasswordDTO updatePasswordDTO);

    String signinGooglePlus(GooglePlusVM googlePlusVM) throws IOException;

    String signinFacebook(FacebookVM facebookVM);

    String signinGooglePlusOneTap(GooglePlusVM googlePlusVM) throws IOException;

    Boolean requestPasswordReset(String mail);

    void completePasswordReset(String password, String key);

    Page<UserDTO> findByCriteria(UserFilter userFilter, Pageable pageable);

    void blockedUserByAdmin(Long id, String blockUnblock);

    // void addRemoveAdmin(Long id, String addRemove);

    ProfileFavoriteUserDTO findWithFavoriteUserById(Long id);

    void updateUserAuthority(Long id, Set<Authority> authorities);

    JwtResponseVM refreshToken(TokenRefreshRequestDTO request);
}
