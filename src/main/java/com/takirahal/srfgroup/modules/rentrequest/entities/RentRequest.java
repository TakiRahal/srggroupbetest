package com.takirahal.srfgroup.modules.rentrequest.entities;

import com.takirahal.srfgroup.modules.offer.entities.RentOffer;
import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_rent_request")
public class RentRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_name_rental", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "send_date")
    private Instant sendDate;

    @Column(name = "status")
    private String status;

    @Lob
    // @Type(type = "org.hibernate.type.TextType")
    @Column(name = "imageSignatureReceived")
    private String imageSignatureReceived;

    @OneToOne
    private RentOffer rentOffer;

    @OneToOne
    private User senderUser;

    @OneToOne
    private User receiverUser;
}
