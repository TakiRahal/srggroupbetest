package com.takirahal.srfgroup.modules.websocket.controllers;

import com.takirahal.srfgroup.modules.websocket.services.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/websocket/")
public class WebSocketRestController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketRestController.class);

    @Autowired
    WebSocketService webSocketService;


    @GetMapping("connected-users")
    public ResponseEntity<List<Principal>> getAllConnectedUsers() {
        log.info("REST request to get all connected users: {}");
        List<Principal> allConnectedUsers = webSocketService.getAllConnectedUsers();
        return new ResponseEntity<>(allConnectedUsers, HttpStatus.OK);
    }
}
