package com.emart.emart.controllers.checkout;

import com.emart.emart.models.Checkout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CheckoutController {
    ResponseEntity<Object> createCheckout(@RequestBody Checkout checkout, @PathVariable Long userId);
    ResponseEntity<Object> viewCheckout(@PathVariable Long userId);
    ResponseEntity<Object> updateCheckout(@RequestBody Checkout checkout, @PathVariable Long userId);
    ResponseEntity<Object> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer noOfItems);
    ResponseEntity<Object> removeFromCart(@RequestParam Long userId, @RequestParam Long productId);
}
