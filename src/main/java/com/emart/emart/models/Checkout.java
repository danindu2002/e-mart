package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkoutId;

    private Date checkoutDate;

    private Double total = 0.0;

    @ManyToMany
    private List<Product> productList;

    @ManyToOne
    private User user;
}
