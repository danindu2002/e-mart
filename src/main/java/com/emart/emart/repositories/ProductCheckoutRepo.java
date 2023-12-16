package com.emart.emart.repositories;

import com.emart.emart.models.Checkout;
import com.emart.emart.models.Product;
import com.emart.emart.models.ProductCheckout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCheckoutRepo extends JpaRepository<ProductCheckout, Long> {
    ProductCheckout findByCheckoutAndProduct(Checkout checkout, Product product);
}
