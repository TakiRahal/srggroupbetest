package com.takirahal.srfgroup.modules.permission.controller;

import com.takirahal.srfgroup.modules.aboutus.dto.AboutUsDTO;
import com.takirahal.srfgroup.modules.aboutus.dto.filter.AboutUsFilter;
import com.takirahal.srfgroup.modules.permission.dto.PermissionDTO;
import com.takirahal.srfgroup.modules.permission.dto.filter.PermissionFilter;
import com.takirahal.srfgroup.modules.permission.services.PermissionService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission/")
public class PermissionController {
    private final Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    PermissionService permissionService;

    @PostMapping("admin/create")
    public ResponseEntity<PermissionDTO> create(@RequestBody PermissionDTO permissionDTO){
        log.info("REST request to save Permission : {}", permissionDTO);
        PermissionDTO result = permissionService.save(permissionDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }


    @GetMapping("/admin")
    public ResponseEntity<Page<PermissionDTO>> getAllPErmissions(PermissionFilter permissionFilter, Pageable pageable) {
        log.info("REST request to get Permission by criteria: {}", permissionFilter);
        Page<PermissionDTO> page = permissionService.findByCriteria(permissionFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("/admin/{id}")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable Long id) {
        log.info("REST request to get Permission : {}", id);
        PermissionDTO result = permissionService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        log.info("REST request to update Permission : {}", id);
        PermissionDTO result = permissionService.update(id, permissionDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
