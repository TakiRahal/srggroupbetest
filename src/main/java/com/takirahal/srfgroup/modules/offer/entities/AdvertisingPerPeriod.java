package com.takirahal.srfgroup.modules.offer.entities;

import com.takirahal.srfgroup.modules.offer.enums.ModulePubOffer;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sg_advertising_per_month")
public class AdvertisingPerPeriod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorAdvertisingPerPeriod", sequenceName = "sequence_name_offer", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorAdvertisingPerPeriod")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "available")
    private boolean available;

    @Column(name = "module")
    private ModulePubOffer module;

    @ManyToOne
    private Offer offer;
}
