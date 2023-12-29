package com.emart.emart.controllers.productDocument;

import com.emart.emart.dtos.productDocumentDtos.ProductDocumentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductDocumentController {
    ResponseEntity<Object> saveDocument(@RequestBody ProductDocumentDto productDocumentDto, @PathVariable Long userId);
    ResponseEntity<Object> deleteDocument(@RequestParam Long documentId, @PathVariable Long userId);
    ResponseEntity<Object> viewAllDocumentsByProductId(@RequestParam Long productId);
    ResponseEntity<Object> viewDocument(@RequestParam Long documentId);
}
