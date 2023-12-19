package com.emart.emart.dtos;

import lombok.Data;

@Data
public class ProductDocumentDetailsDto {
    private Long documentId;
    private String documentName;
    private String documentDescription;
    private String productName;
    private String productCode;
}
