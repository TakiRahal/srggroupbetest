package com.takirahal.srfgroup.modules.websocket.services.impl;

import com.takirahal.srfgroup.modules.websocket.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    SimpUserRegistry userRegistry;

    @Override
    public List<Principal> getAllConnectedUsers() {
        Set<SimpUser> simpUsers = this.userRegistry.getUsers();
        List<Principal> connectedUsers = new ArrayList<>();
        simpUsers.stream().forEach(user -> {
            connectedUsers.add(user.getPrincipal());
        });
        return connectedUsers;
    }
}
