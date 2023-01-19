package com.takirahal.srfgroup.modules.permission.services;

import com.takirahal.srfgroup.modules.permission.dto.PermissionDTO;
import com.takirahal.srfgroup.modules.permission.dto.filter.PermissionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {

    /**
     *
     * @param permissionDTO
     * @return
     */
    PermissionDTO save(PermissionDTO permissionDTO);

    /**
     *
     * @param permissionFilter
     * @param pageable
     * @return
     */
    Page<PermissionDTO> findByCriteria(PermissionFilter permissionFilter, Pageable pageable);

    /**
     *
     * @param id
     * @return
     */
    PermissionDTO findById(Long id);

    PermissionDTO update(Long id, PermissionDTO permissionDTO);
}
