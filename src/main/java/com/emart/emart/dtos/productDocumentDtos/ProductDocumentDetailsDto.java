package com.emart.emart.dtos.productDocumentDtos;

import lombok.Data;

@Data
public class ProductDocumentDetailsDto {
    private Long documentId;
    private String documentName;
    private String documentDescription;
    private String productName;
    private String productCode;
}
