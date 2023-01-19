package com.takirahal.srfgroup.utils;

import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import com.takirahal.srfgroup.modules.user.entities.User;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.security.UserPrincipal;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getEmailByCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static Long getIdByCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipalId(securityContext.getAuthentication()))
                .orElseThrow(() -> new AccountResourceException("Current user not found"));
    }

    private static Long extractPrincipalId(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal springSecurityUser = (UserPrincipal) authentication.getPrincipal();
            return springSecurityUser.getId();
        }
        return null;
    }

    public static Optional<UserPrincipal> getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipalCurrentUser(securityContext.getAuthentication()));
    }

    private static UserPrincipal extractPrincipalCurrentUser(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserPrincipal springSecurityUser = (UserPrincipal) authentication.getPrincipal();
            return springSecurityUser;
        }
        return null;
    }

    public static Optional<String> getEmailCurrentUser() {
        return Optional.ofNullable(getCurrentUser().get().getEmail());
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getJWTCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
            .ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getAuthorities().noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    /**
     * Checks if the current user has a specific authority.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasCurrentUserThisAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getAuthorities().anyMatch(authority::equals);
    }

    public static Stream<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null ){
            return null;
        }
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }

    public static boolean hasUserThisAuthority(Set<Authority> authorities, String authority) {
        return authorities.stream().anyMatch((e) -> (e.getName().equals(authority)));
    }


    /**
     * Protected Super Admin: User should not Super Admin
     * @param user
     */
    public static void protectedAdminAndSuperAdmin(User user){
        /*
        user.getAuthorities().stream().forEach(authority -> {
            Authority authorityUserSA = new Authority();
            authorityUserSA.setName(AuthoritiesConstants.SUPER_ADMIN);

            Authority authorityUserAD = new Authority();
            authorityUserAD.setName(AuthoritiesConstants.ADMIN);

            if(authority.equals(authorityUserSA) || authority.equals(authorityUserAD) ){
                throw new UnauthorizedException("User Admin / Super admin");
            }
        });
        */
    }


    public static void protectedSuperAdmin(User user){
        /*
        user.getAuthorities().stream().forEach(authority -> {
            Authority authorityUserSA = new Authority();
            authorityUserSA.setName(AuthoritiesConstants.SUPER_ADMIN);
            if(authority.equals(authorityUserSA)){
                throw new UnauthorizedException("User super admin");
            }
        });
        */
    }
}
