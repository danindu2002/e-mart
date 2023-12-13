package com.emart.emart.services.checkout;

import com.emart.emart.models.Checkout;

public interface CheckoutService {
    void createCart();
    Checkout viewCart(Long cartId);


}
