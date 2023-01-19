package com.takirahal.srfgroup.modules.favoriteuser.entities;

import com.takirahal.srfgroup.modules.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_favorite_user")
public class FavoriteUser {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGeneratorFavoriteUser", sequenceName = "sequence_name_favorite_user", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGeneratorFavoriteUser")
    private Long id;

    @Column(name = "favorite_date")
    private Instant favoriteDate;

    @ManyToOne
    private User currentUser;

    @ManyToOne
    private User favoriteUser;
}
