package com.emart.emart.repositories;

import com.emart.emart.models.Checkout;
import com.emart.emart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout, Long> {
    Checkout findByUserAndOrderedIsFalse(User user);
    Checkout findByCheckoutId(Long checkoutId);
}
