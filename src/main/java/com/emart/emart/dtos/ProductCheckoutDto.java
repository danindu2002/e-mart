package com.emart.emart.dtos;

import lombok.Data;

@Data
public class ProductCheckoutDto {
    private Long productId;
    private String productName;
    private String productCode;
    private Double price;
    private String size;
    private String color;
    private Integer noOfItems;
    private Double subTotal;
}
