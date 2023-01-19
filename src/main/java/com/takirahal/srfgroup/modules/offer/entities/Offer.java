package com.takirahal.srfgroup.modules.offer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takirahal.srfgroup.modules.address.entities.Address;
import com.takirahal.srfgroup.modules.category.entities.Category;
import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Types;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_offer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_offer", length = 255)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorOffer", sequenceName = "sequence_name_offer", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorOffer")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "date_created", updatable = false)
    private Instant dateCreated;

    @Column(name = "type_offer", insertable = false, updatable = false)
    private String typeOffer;

    @Column(name = "blocked_by_reported")
    private Boolean blockedByReported = false;

    @Column(name = "available")
    private Boolean available = true;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "offer", orphanRemoval = true)
    @JsonIgnoreProperties(value = { "offer" }, allowSetters = true)
    private Set<OfferImages> offerImages = new HashSet<>();

    @ManyToOne
    private Address address;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subCategories" }, allowSetters = true)
    private Category category;

}
