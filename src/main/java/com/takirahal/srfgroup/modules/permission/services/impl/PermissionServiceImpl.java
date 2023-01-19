package com.takirahal.srfgroup.modules.permission.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.permission.controller.PermissionController;
import com.takirahal.srfgroup.modules.permission.dto.PermissionDTO;
import com.takirahal.srfgroup.modules.permission.dto.filter.PermissionFilter;
import com.takirahal.srfgroup.modules.permission.entities.Permission;
import com.takirahal.srfgroup.modules.permission.mapper.PermissionMapper;
import com.takirahal.srfgroup.modules.permission.repositories.PermissionRepository;
import com.takirahal.srfgroup.modules.permission.services.PermissionService;
import com.takirahal.srfgroup.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        log.debug("Request to save Permission : {}", permissionDTO);

        Optional<Permission> permissionExist = permissionRepository.findByName(permissionDTO.getName());
        if( permissionExist.isPresent() ){
            throw new BadRequestAlertException("Permission already exist");
        }

        Permission permission = permissionMapper.toEntity(permissionDTO);
        permission = permissionRepository.save(permission);
        return permissionMapper.toDto(permission);
    }

    @Override
    public Page<PermissionDTO> findByCriteria(PermissionFilter permissionFilter, Pageable pageable) {
        log.debug("Request to findByCriteria Permission : {}", permissionFilter);
        return permissionRepository.findAll(createSpecification(permissionFilter), pageable).map(permissionMapper::toDto);
    }

    @Override
    public PermissionDTO findById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("common.resource_not_found")));
        return permissionMapper.toDto(permission);
    }

    @Override
    public PermissionDTO update(Long id, PermissionDTO permissionDTO) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("common.resource_not_found")));

        permission.setName(permissionDTO.getName());
        permission.setPathApi(permissionDTO.getPathApi());
        permission.setDescription(permissionDTO.getDescription());

        Permission permissionUpdate = permissionRepository.save(permission);
        return permissionMapper.toDto(permissionUpdate);
    }

    private Specification<Permission> createSpecification(PermissionFilter permissionFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
