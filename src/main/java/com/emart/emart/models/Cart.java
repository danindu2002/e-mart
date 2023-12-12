package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Integer noOfItems;
    private Double subTotal;

    @ManyToMany
    private List<Product> productList;
}
