package com.emart.emart.repositories;

import com.emart.emart.models.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout, Long> {
}
