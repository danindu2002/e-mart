package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_checkout")
public class ProductCheckout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCheckoutId;

    private Integer noOfItems;

    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private Checkout checkout;
}
