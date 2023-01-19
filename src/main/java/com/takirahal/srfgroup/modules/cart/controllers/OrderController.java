package com.takirahal.srfgroup.modules.cart.controllers;

import com.takirahal.srfgroup.modules.cart.dto.OrderDTO;
import com.takirahal.srfgroup.modules.cart.dto.filter.CartFilter;
import com.takirahal.srfgroup.modules.cart.dto.filter.OrderFilter;
import com.takirahal.srfgroup.modules.cart.services.OrderService;
import com.takirahal.srfgroup.modules.user.annotations.ConnectedProfile;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @GetMapping("current-user/passed")
    public ResponseEntity<Page<OrderDTO>> getOrdersPassedByCurrentUser(OrderFilter orderFilter, Pageable pageable) {
        log.info("REST request to get all passed Orders by criteria: {}", orderFilter);
        Page<OrderDTO> page = orderService.getOrdersPassedByCurrentUser(orderFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("current-user/received")
    public ResponseEntity<Page<OrderDTO>> getOrdersReceivedByCurrentUser(OrderFilter orderFilter, Pageable pageable, @ConnectedProfile UserPrincipal userPrincipal) {
        log.info("REST request to get all received Orders by criteria: {}");
        Page<OrderDTO> page = orderService.getOrdersReceivedByCurrentUser(orderFilter, pageable, userPrincipal);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO, @ConnectedProfile UserPrincipal userPrincipal) {
        log.info("REST request to save Order : {}");
        OrderDTO result = orderService.save(userPrincipal, orderDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
