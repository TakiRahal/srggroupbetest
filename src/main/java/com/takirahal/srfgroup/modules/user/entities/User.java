package com.takirahal.srfgroup.modules.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takirahal.srfgroup.modules.address.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Types;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
})
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_name_user", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotBlank
    @Size(max = 40)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotNull
    @Column(name = "activated_account", nullable = false)
    private boolean activatedAccount = false;

    @Column(name = "blocked")
    private String blocked = "";

    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(name = "image_url", columnDefinition="TEXT")
    private String imageUrl;

    @Size(max = 50)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 50)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Size(min = 2, max = 50)
    @Column(name = "lang_key", length = 20)
    private String langKey;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 30)
    @Column(name = "source_connected_device", length = 20)
    private String sourceConnectedDevice;

    @JsonIgnore
    @NotBlank
    @Size(max = 100)
    private String password;

    @Column(name = "register_date")
    private Instant registerDate;

    @Size(max = 400)
    @Column(name = "link_profile_facebook", length = 400)
    private String linkProfileFacebook;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authorities",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @ManyToOne
    private Address address;

}
