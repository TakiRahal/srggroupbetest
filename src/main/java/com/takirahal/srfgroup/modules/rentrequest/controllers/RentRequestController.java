package com.takirahal.srfgroup.modules.rentrequest.controllers;

import com.takirahal.srfgroup.modules.cart.controllers.CartController;
import com.takirahal.srfgroup.modules.cart.dto.CartDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import com.takirahal.srfgroup.modules.cart.models.DetailsCarts;
import com.takirahal.srfgroup.modules.rentrequest.dto.RentRequestDTO;
import com.takirahal.srfgroup.modules.rentrequest.dto.filter.RentRequestFilter;
import com.takirahal.srfgroup.modules.rentrequest.entities.RentRequest;
import com.takirahal.srfgroup.modules.rentrequest.services.RentRequestService;
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
@RequestMapping("/api/rentrequest/")
public class RentRequestController {

    private final Logger log = LoggerFactory.getLogger(RentRequestController.class);

    @Autowired
    RentRequestService rentRequestService;

    @PostMapping("create")
    public ResponseEntity<RentRequestDTO> createRentRequest(@RequestBody RentRequestDTO rentRequestDTO) {
        log.info("REST request to save RentRequest : {}", rentRequestDTO);
        RentRequestDTO result = rentRequestService.save(rentRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @GetMapping("current-user/sent")
    public ResponseEntity<Page<RentRequestDTO>> getCartsByCurrentUserSent(RentRequestFilter rentRequestFilter, Pageable pageable) {
        log.info("REST request to get RentRequest by criteria: {}", rentRequestFilter);
        Page<RentRequestDTO> page = rentRequestService.getCartsByCurrentUserSent(rentRequestFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("current-user/received")
    public ResponseEntity<Page<RentRequestDTO>> getCartsByCurrentUserReceived(RentRequestFilter rentRequestFilter, Pageable pageable) {
        log.info("REST request to get RentRequest by criteria: {}", rentRequestFilter);
        Page<RentRequestDTO> page = rentRequestService.getCartsByCurrentUserReceived(rentRequestFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteRentRequestSent(@PathVariable Long id) {
        log.info("REST request to delete Rent Request Sent : {}", id);
        rentRequestService.delete(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("rentrequest.rentrequest_delete_succefully", id.toString()), HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @return
     */
    @PutMapping("refused-received/{id}")
    public ResponseEntity<Boolean> refusedRentRequestReceived(@PathVariable Long id) {
        log.info("REST request to refused RentRequest From Received : {}", id);
        rentRequestService.refusedRentRequestReceived(id);

        return new ResponseEntity<>(true, HeaderUtil.createAlert("rentrequest.rentrequest_refused_succefully", id.toString()), HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @return
     */
    @PutMapping("accept-received/{id}")
    public ResponseEntity<Boolean> acceptRentRequestReceived(@PathVariable Long id, @RequestBody RentRequestDTO rentRequestDTO) {
        log.info("REST request to refused RentRequest From Received : {}", id);
        rentRequestService.acceptRentRequestReceived(id, rentRequestDTO);

        return new ResponseEntity<>(true, HeaderUtil.createAlert("rentrequest.rentrequest_accepted_succefully", id.toString()), HttpStatus.OK);
    }
}
