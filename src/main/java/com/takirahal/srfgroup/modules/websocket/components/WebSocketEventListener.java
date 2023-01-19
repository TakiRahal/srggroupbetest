package com.takirahal.srfgroup.modules.websocket.components;

import com.takirahal.srfgroup.modules.websocket.dto.ActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.debug("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.debug("Handle WebSocket DisconnectListener");

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String email = (String) headerAccessor.getSessionAttributes().get("emailConnectedUser");
        logger.debug("User Status : " + email);
        if(email != null) {
            logger.debug("User Disconnected : " + email);

            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setSessionId(event.getSessionId());
            activityDTO.setNameModule("DisconnectedUser");
            activityDTO.setUserEmail(email);

            messagingTemplate.convertAndSend("/topic/disconnected-user", activityDTO);
        }
    }
}
