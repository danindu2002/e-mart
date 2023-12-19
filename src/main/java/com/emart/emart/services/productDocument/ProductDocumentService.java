package com.emart.emart.services.productDocument;

import com.emart.emart.dtos.productDocumentDtos.ProductDocumentDetailsDto;
import com.emart.emart.dtos.productDocumentDtos.ProductDocumentDto;
import com.emart.emart.models.ProductDocument;

import java.util.List;

public interface ProductDocumentService {
    int saveProductDocument(ProductDocumentDto productDocumentDto);
    int deleteDocument(Long documentId);
    List<ProductDocumentDetailsDto> viewAllDocuments(Long productId);
    ProductDocumentDto viewDocument(Long documentId);
    ProductDocumentDto mapTopDocumentDto(ProductDocument productDocument);
    ProductDocumentDetailsDto mapToDocumentDetailsDto(ProductDocument productDocument);
    List<ProductDocumentDetailsDto> mapToDocumentDetailsDtoList(List<ProductDocument> productDocument);
}
