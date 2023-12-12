package com.emart.emart.models.ref;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refCategory")
@Data
public class RefCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refCategoryId;

    @Column(nullable = false, unique = true)
    private String refCategoryName;
}