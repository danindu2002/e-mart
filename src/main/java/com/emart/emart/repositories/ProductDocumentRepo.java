package com.emart.emart.repositories;

import com.emart.emart.models.Product;
import com.emart.emart.models.ProductDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDocumentRepo extends JpaRepository<ProductDocument, Long> {
    ProductDocument findByDocumentNameAndProductAndDeletedIsFalse(String documentName, Product product);
    ProductDocument findByDocumentIdAndDeletedIsFalse(Long documentId);
    List<ProductDocument> findAllByProductAndDeletedIsFalse(Product product);

}
