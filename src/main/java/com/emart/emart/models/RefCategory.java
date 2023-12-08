package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refCategory")
@Data
public class RefCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refCategoryId;

    private String refCategoryName;
}
