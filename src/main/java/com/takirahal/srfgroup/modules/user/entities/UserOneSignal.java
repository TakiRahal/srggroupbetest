package com.takirahal.srfgroup.modules.user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_user_one_signal")
public class UserOneSignal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "id_one_signal", length = 50)
    private String idOneSignal;

    @Size(max = 30)
    @Column(name = "source_connected_device", length = 20)
    private String sourceConnectedDevice;

    @Column(name = "register_date")
    private Instant registerDate;

    @ManyToOne
    private User user;
}
