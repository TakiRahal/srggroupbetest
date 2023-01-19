package com.takirahal.srfgroup.modules.user.controllers;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.permission.dto.UpdateUserAuthorityDTO;
import com.takirahal.srfgroup.modules.user.dto.AuthorityDTO;
import com.takirahal.srfgroup.modules.user.entities.Authority;
import com.takirahal.srfgroup.modules.user.repositories.AuthorityRepository;
import com.takirahal.srfgroup.modules.user.services.AuthorityService;
import com.takirahal.srfgroup.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authority/")
public class AuthorityController {

    private final Logger log = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    AuthorityService authorityService;

    @Autowired
    AuthorityRepository authorityRepository;

    @GetMapping("admin")
    public ResponseEntity<Page<Authority>> getAllAuthorities(Pageable pageable) {
        log.info("REST request to get Permission by criteria: {}", pageable);
        Page<Authority> page = authorityRepository.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("admin")
    public ResponseEntity<AuthorityDTO> createAuthority(@RequestBody AuthorityDTO authorityDTO) {
        log.info("REST request to save new Authority: {}", authorityDTO);
        AuthorityDTO result = authorityService.save(authorityDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("admin/{id}")
    public ResponseEntity<Authority> getDetailsAuthority(@PathVariable Integer id) {
        log.info("REST request to get Authority: {}", id);
        Authority result = authorityRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("common.resource_not_found")));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("admin/{id}")
    public ResponseEntity<Authority> updateAuthority(@PathVariable Integer id, @RequestBody Authority authority) {
        log.info("REST request to update new Authority: {}", authority);
        Authority auth = authorityRepository.findById(id)
                .orElseThrow(() -> new ResouorceNotFoundException(RequestUtil.messageTranslate("common.resource_not_found")));
        auth.setName(authority.getName());
        auth.setPermissions(authority.getPermissions());

        Authority result = authorityRepository.save(auth);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("user-authority/{id}")
    public ResponseEntity<Boolean> updateUserAuthority(@PathVariable Long id, @RequestBody UpdateUserAuthorityDTO updateUserAuthorityDTO) {
        log.info("REST request to update User Authority : {}", id);
        Boolean result = authorityService.updateUserAuthority(id, updateUserAuthorityDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
