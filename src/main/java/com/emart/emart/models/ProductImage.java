package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productImage")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String imagePath;

    @Column
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}