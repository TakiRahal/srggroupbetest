package com.takirahal.srfgroup.modules.address.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_address")
public class Address  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorAddress", sequenceName = "sequence_name_address", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorAddress")
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "country")
    private String country;

    @Column(name = "iso_2")
    private String iso2;

    @Column(name = "admin_name")
    private String admin_name;

    @Column(name = "capital")
    private String capital;

    @Column(name = "population")
    private String population;

    @Column(name = "population_proper")
    private String population_proper;
}
