package com.takirahal.srfgroup.modules.websocket.controllers;

import com.takirahal.srfgroup.modules.chat.dto.MessageDTO;
import com.takirahal.srfgroup.modules.websocket.dto.ActivityDTO;
import com.takirahal.srfgroup.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.Instant;

import static com.takirahal.srfgroup.config.WebSocketConfig.IP_ADDRESS;

@Controller
public class WebsocketController implements ApplicationListener<SessionDisconnectEvent> {
    private static final Logger log = LoggerFactory.getLogger(WebsocketController.class);

    @Autowired
    SimpMessageSendingOperations messagingTemplate;


    /**
     *
     * @param activityDTO
     * @param stompHeaderAccessor
     * @param principal
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/topic/user.connectedUser")
    @SendTo("/topic/connected-user")
    public ActivityDTO connectedUser(@Payload ActivityDTO activityDTO,
                                     StompHeaderAccessor stompHeaderAccessor,
                                     Principal principal,
                                     SimpMessageHeaderAccessor headerAccessor) {

        log.debug("New user connected with email: {}", activityDTO.getUserEmail());

        // Add username in web socket session
        activityDTO.setUserEmail(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        activityDTO.setTime(Instant.now());
        activityDTO.setNameModule("ConnectedUser");
        headerAccessor.getSessionAttributes().put("emailConnectedUser", activityDTO.getUserEmail());
        return activityDTO;
    }


    /**
     *
     * @param messageDTO
     * @param stompHeaderAccessor
     * @param principal
     * @return
     */
    @MessageMapping("/topic/sendChatMessages")
    public void sendChatMessages(@Payload MessageDTO messageDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
//        activityDTO.setUserEmail(principal.getName());
//        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
//        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
//        activityDTO.setTime(Instant.now());
//        log.info("Sending user tracking data {}", activityDTO);
//        return activityDTO;
        // log.info("/topic/chat-message {}", ((UserPrincipal) principal).getId());
//        UserPrincipal userPrincipal = (UserPrincipal)(((UsernamePasswordAuthenticationToken) principal).getPrincipal());
//        Long receiverId = messageDTO.getReceiverUser().getId().equals(userPrincipal.getId()) ? messageDTO.getSenderUser().getId() : messageDTO.getReceiverUser().getId();
//        Long senderId = messageDTO.getSenderUser().getId().equals(userPrincipal.getId()) ? messageDTO.getReceiverUser().getId() : messageDTO.getSenderUser().getId();
        messagingTemplate.convertAndSend("/topic/chat-message/"+messageDTO.getReceiverUser().getId()+"/"+messageDTO.getSenderUser().getId(), messageDTO);
    }


    /**
     *
     * @param activityDTO
     * @param stompHeaderAccessor
     * @param principal
     * @return
     */
    @MessageMapping("/topic/activity")
    @SendTo("/topic/tracker")
    public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        activityDTO.setUserEmail(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        activityDTO.setTime(Instant.now());
        log.info("Sending user tracking data {}", activityDTO);
        return activityDTO;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSessionId(event.getSessionId());
        activityDTO.setNameModule("logout");
        log.info("logout {}", event.getSessionId());
        messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
    }
}
