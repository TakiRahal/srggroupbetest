package com.takirahal.srfgroup.modules.aboutus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Lob;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutUsDTO implements Serializable {

    private Long id;

    @Lob
    private String contentAr;

    @Lob
    private String contentEn;

    @Lob
    private String contentFr;
}
