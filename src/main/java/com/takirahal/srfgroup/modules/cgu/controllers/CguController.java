package com.takirahal.srfgroup.modules.cgu.controllers;

import com.takirahal.srfgroup.modules.cgu.dto.CguDTO;
import com.takirahal.srfgroup.modules.cgu.services.CguService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cgu/")
public class CguController {

    private final Logger log = LoggerFactory.getLogger(CguController.class);

    @Autowired
    CguService cguService;

    @PostMapping("create")
    public ResponseEntity<CguDTO> createCgu(@RequestBody CguDTO cguDTO) {
        log.info("REST request to update Cgu : {}", cguDTO);
        CguDTO result = cguService.save(cguDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("cart.message_cart_added_successfully", ""), HttpStatus.CREATED);
    }


    @GetMapping("/public")
    public ResponseEntity<CguDTO> geCgu() {
        log.debug("REST request to get Cgu : {}");
        CguDTO result = cguService.getCgu();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CguDTO> updateCgu(@PathVariable Long id, @RequestBody CguDTO cguDTO) {
        log.info("REST request to update Cgu : {}", cguDTO);
        CguDTO result = cguService.update(id, cguDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("cart.message_cart_added_successfully", ""), HttpStatus.CREATED);
    }
}
