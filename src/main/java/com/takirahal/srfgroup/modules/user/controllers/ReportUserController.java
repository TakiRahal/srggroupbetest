package com.takirahal.srfgroup.modules.user.controllers;

import com.takirahal.srfgroup.modules.user.services.ReportUserService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report-user/")
public class ReportUserController {

    private final Logger log = LoggerFactory.getLogger(ReportUserController.class);

    @Autowired
    ReportUserService reportUserService;

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("create/{id}")
    public ResponseEntity<Boolean> addUpdate(@PathVariable Long id){
        log.debug("REST request to report user : {}", id);
        boolean result = reportUserService.addUpdate(id);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("profile.message_report_successfully", ""), HttpStatus.CREATED);
    }
}
