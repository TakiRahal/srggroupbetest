package com.takirahal.srfgroup.modules.user.services;

import com.takirahal.srfgroup.modules.permission.dto.UpdateUserAuthorityDTO;
import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import com.takirahal.srfgroup.modules.user.dto.AuthorityDTO;

public interface AuthorityService {

    /**
     *
     * @param authorityDTO
     * @return
     */
    AuthorityDTO save(AuthorityDTO authorityDTO);

    /**
     *
     * @param id
     * @param updateUserAuthorityDTO
     * @return
     */
    Boolean updateUserAuthority(Long id, UpdateUserAuthorityDTO updateUserAuthorityDTO);


    /**
     *
     * @param permission
     */
    void checkForPermissions(EPermission permission);
}
