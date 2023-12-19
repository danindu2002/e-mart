package com.emart.emart.dtos;

import com.emart.emart.dtos.productCheckoutDtos.ProductCheckoutDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CheckoutDto {
    private Long checkoutId;
    private Date checkoutDate;
    private List<ProductCheckoutDto> productsList;
    private UserDto user;
    private Double total;
}
