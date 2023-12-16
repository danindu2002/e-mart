package com.emart.emart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Boolean ordered = false;

//    @JsonIgnore
    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCheckout> productCheckouts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}