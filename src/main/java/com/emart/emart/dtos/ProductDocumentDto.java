package com.emart.emart.dtos;

import lombok.Data;

@Data
public class ProductDocumentDto {
    private String documentName;
    private String documentDescription;
    private String document;
    private Long productId;
}