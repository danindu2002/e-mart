package com.emart.emart.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.List;
@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productCode;
    private String description;
    private String category;
    private Integer quantity;
    private Double rating;
    private Double price;
    private String size;
    private String color;
    private String documentPath;
//    private String productImagesPath;
}
