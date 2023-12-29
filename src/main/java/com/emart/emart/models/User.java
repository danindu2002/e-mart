package com.emart.emart.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contactNo;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "mediumtext")
    private String profilePhoto = "";

    @Column
    private Boolean deleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Checkout> checkoutList;

}
