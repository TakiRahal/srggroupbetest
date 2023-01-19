package com.takirahal.srfgroup.modules.contactus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactUsDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String subject;

    @Lob
    private String message;

    @Lob
    private String captchaResponse;
}
