package com.emart.emart.services.checkout;

import com.emart.emart.dtos.CheckoutDto;
import com.emart.emart.dtos.productCheckoutDtos.ProductCheckoutDto;
import com.emart.emart.models.Checkout;
import com.emart.emart.models.ProductCheckout;

import java.util.List;

public interface CheckoutService {
    int createCheckout(Checkout checkout, Long userId);
    CheckoutDto viewCheckout(Long userId);
    CheckoutDto viewCheckoutById(Long checkoutId);
    int updateCheckout(Checkout checkout, Long userId);
    int addToCart(Long userId, Long productId, Integer noOfItems);
    int removeFromCart(Long userId, Long productId);
    CheckoutDto convertToCheckoutDto(Checkout checkout);
    ProductCheckoutDto convertToProductCheckoutDto(ProductCheckout productCheckout);
    List<ProductCheckoutDto> convertToProductCheckoutDtoList(List<ProductCheckout> productCheckoutList);

}
