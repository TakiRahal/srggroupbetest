package com.takirahal.srfgroup.modules.user.dto;


import com.takirahal.srfgroup.modules.user.models.PictureFacebook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookVM {

    private String accessToken;
    private String data_access_expiration_time;
    private String email;
    private String expiresIn;
    private String graphDomain;
    private String id;
    private String name;
    private PictureFacebook picture;
    private String signedRequest;
    private String userID;
    private String sourceConnectedDevice;
    private String idOneSignal;
}
