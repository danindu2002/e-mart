package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refRole")
@Data
public class RefRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refRoleId;

    private String refRoleName;

}
