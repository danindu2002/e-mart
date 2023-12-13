package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false, unique = true)
    private String productCode; // eg: #100234

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantity; // total number of items in the stock

    @Column
    private Double rating;

    @Column(nullable = false)
    private Double price;

    @Column
    private String size;

    @Column
    private String color;

    @Column(nullable = false)
    private String category;

    @Column
    private Integer noOfItems = 0; // no of items added to the cart

//    @ElementCollection
//    private List<String> productImages;

    @Column
    private Boolean deleted = false;
}
