package com.takirahal.srfgroup.modules.contactus.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * A ContactUs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_contact_us")
public class ContactUs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorContactUs", sequenceName = "sequence_name_contact_us", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorContactUs")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "subject")
    private String subject;

    @Lob
    // @Type(type = "org.hibernate.type.TextType")
    @Column(name = "message")
    private String message;
}
