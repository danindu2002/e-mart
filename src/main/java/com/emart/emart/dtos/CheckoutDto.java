package com.emart.emart.dtos;

import com.emart.emart.models.User;
import lombok.Data;

import java.util.List;

@Data
public class CheckoutDto {
    private Long cartId;
    private List<ProductDto> productDtoList;
    private User user;
    private Double subTotal;
}
