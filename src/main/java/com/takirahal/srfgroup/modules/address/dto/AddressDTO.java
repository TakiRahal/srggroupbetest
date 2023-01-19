package com.takirahal.srfgroup.modules.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {
    private Long id;

    private String city;

    private Double lat;

    private Double lng;

    private String country;

    private String iso2;

    private String admin_name;

    private String capital;

    private String population;

    private String population_proper;
}
