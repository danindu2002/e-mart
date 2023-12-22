package com.emart.emart.dtos.productImageDtos;

import lombok.Data;

@Data
public class ProductImageDto {
    private Long imageId;
    private String imageName;
    private String image;
    private Long productId;
}
