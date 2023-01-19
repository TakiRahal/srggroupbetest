package com.takirahal.srfgroup.modules.user.controllers;

import com.takirahal.srfgroup.modules.rentrequest.dto.RentRequestDTO;
import com.takirahal.srfgroup.modules.rentrequest.dto.filter.RentRequestFilter;
import com.takirahal.srfgroup.modules.user.dto.UserOneSignalDTO;
import com.takirahal.srfgroup.modules.user.entities.UserOneSignal;
import com.takirahal.srfgroup.modules.user.services.UserOneSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/onesignal/")
public class UserOneSignalController {

    private final Logger log = LoggerFactory.getLogger(UserOneSignalController.class);


    @Autowired
    UserOneSignalService userOneSignalService;

    @GetMapping("for-user/{id}")
    public ResponseEntity<List<UserOneSignal>> getOneSignalByUserId(@PathVariable Long id) {
        log.info("REST request to get OneSignal by user id : {}", id);
        List<UserOneSignal> result = userOneSignalService.findOneSignalByUserId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
