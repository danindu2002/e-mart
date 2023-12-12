package com.emart.emart.models.ref;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refRole")
@Data
public class RefRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refRoleId;

    @Column(nullable = false, unique = true)
    private String refRoleName;

}
