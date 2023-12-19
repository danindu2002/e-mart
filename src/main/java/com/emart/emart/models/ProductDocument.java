package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productDocument")
public class ProductDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(nullable = false)
    private String documentName;

    @Column(nullable = false)
    private String documentDescription;

    @Column(nullable = false)
    private String documentPath;

    @Column
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
