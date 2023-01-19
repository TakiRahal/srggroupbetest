package com.takirahal.srfgroup.modules.websocket.services;

import java.security.Principal;
import java.util.List;

public interface WebSocketService {
    List<Principal> getAllConnectedUsers();
}
