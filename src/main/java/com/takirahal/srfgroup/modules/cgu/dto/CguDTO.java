package com.takirahal.srfgroup.modules.cgu.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class CguDTO implements Serializable {
    private Long id;
    private Instant modifyDate;
    private String contentAr;
    private String contentFr;
    private String contentEn;
}
