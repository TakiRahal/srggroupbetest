package com.takirahal.srfgroup.modules.websocket.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectedUser {

    String name;
    Principal principal;
}
