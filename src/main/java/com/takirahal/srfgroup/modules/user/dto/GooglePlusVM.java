package com.takirahal.srfgroup.modules.user.dto;

import com.takirahal.srfgroup.modules.user.models.ProfileObj;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePlusVM {
    private String Ba;
    private String accessToken;
    private String googleId;
    private ProfileObj profileObj;
    private String tokenId;
    private String sourceConnectedDevice;
    private String idOneSignal;
}
