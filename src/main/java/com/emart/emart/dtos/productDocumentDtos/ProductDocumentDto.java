package com.emart.emart.dtos.productDocumentDtos;

import lombok.Data;

@Data
public class ProductDocumentDto {
    private String documentName;
    private String documentDescription;
    private String document;
    private Long productId;
}