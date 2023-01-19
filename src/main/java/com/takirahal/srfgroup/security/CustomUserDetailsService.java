package com.takirahal.srfgroup.security;

import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.enums.BlockedUser;
import com.takirahal.srfgroup.modules.user.exceptioins.UserBlockedException;
import com.takirahal.srfgroup.modules.user.exceptioins.UserNotActivatedException;
import com.takirahal.srfgroup.modules.user.repositories.UserRepository;
import com.takirahal.srfgroup.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        return userRepository
                .findOneByEmailIgnoreCase(usernameOrEmail)
                .map(user -> createSpringSecurityUser(usernameOrEmail, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + usernameOrEmail + " was not found in the database"));
    }

    private UserPrincipal createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivatedAccount()) {
            throw new UserNotActivatedException(RequestUtil.messageTranslate("signin.account_not_activated"));
        }

        if( user.getBlocked()!=null && user.getBlocked().equals(BlockedUser.BlockedByAdmin.toString()) ){
            throw new UserBlockedException("signin.blocked_by_admin");
        }
        else if( user.getBlocked()!=null && user.getBlocked().equals(BlockedUser.BlockedByReport.toString()) ){
            throw new UserBlockedException("signin.blocked_by_report");
        }

        return UserPrincipal.create(user);
    }
}
