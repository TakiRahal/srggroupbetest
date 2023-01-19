package com.takirahal.srfgroup.modules.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    private String sessionId;

    private String userEmail;

    private String ipAddress;

    private String nameModule;

    private Instant time;
}
