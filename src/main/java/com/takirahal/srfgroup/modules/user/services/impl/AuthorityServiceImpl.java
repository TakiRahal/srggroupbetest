package com.takirahal.srfgroup.modules.user.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.exceptions.UnauthorizedException;
import com.takirahal.srfgroup.modules.permission.dto.UpdateUserAuthorityDTO;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import com.takirahal.srfgroup.modules.user.dto.AuthorityDTO;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.enums.EAuthority;
import com.takirahal.srfgroup.modules.user.mapper.AuthorityMapper;
import com.takirahal.srfgroup.modules.user.repositories.AuthorityRepository;
import com.takirahal.srfgroup.modules.user.services.AuthorityService;
import com.takirahal.srfgroup.modules.user.services.UserService;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final Logger log = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityMapper authorityMapper;

    @Override
    public AuthorityDTO save(AuthorityDTO authorityDTO) {
        log.debug("Request to save Authority : {}", authorityDTO);
        Optional<Authority> authorityByName = authorityRepository.findByName(authorityDTO.getName());
        if( authorityByName.isPresent() ){
            throw new BadRequestAlertException("Authority already exist");
        }

        Authority authority = authorityRepository.save(authorityMapper.toEntity(authorityDTO));
        return authorityMapper.toDto(authority);
    }

    @Override
    public Boolean updateUserAuthority(Long id, UpdateUserAuthorityDTO updateUserAuthorityDTO) {

        // Check Permission
        checkForPermissions(EPermission.UPDATE_USER_AUTHORITY);

        if( !id.equals(updateUserAuthorityDTO.getId() )){
            throw new BadRequestAlertException("User invalid");
        }

        Authority authorityByName = authorityRepository.findByName(updateUserAuthorityDTO.getNameAuthority())
                .orElseThrow(() -> new ResouorceNotFoundException("Not found authority by name "+updateUserAuthorityDTO.getNameAuthority().name()));

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityByName);
        userService.updateUserAuthority(id, authorities);
        return true;
    }

    @Override
    public void checkForPermissions(EPermission permission){
        SecurityUtils.getAuthorities().forEach(authority -> {

            if( authority.equals(EAuthority.ROLE_MODERATOR.name() ) ||
                authority.equals(EAuthority.ROLE_ADMIN.name()) ){
                return;
            }

            EAuthority eAuthority = EAuthority.valueOf(authority);
            Authority auth = authorityRepository.findByName(eAuthority)
                    .orElseThrow(() -> new ResouorceNotFoundException("Not found authority by name "+authority));

            auth.getPermissions().stream()
                    .filter(perm -> perm.getName().equals(permission))
                    .findFirst()
                    .orElseThrow(() -> new UnauthorizedException("Aunauthorized action"));
        });
    }
}
