package com.takirahal.srfgroup.modules.permission.entities;

import com.takirahal.srfgroup.modules.permission.enums.EPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sg_permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_name_permission", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 100)
    private EPermission name;

    @Column(name = "pathApi")
    private String pathApi;

    @Lob
    // // @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;
}
