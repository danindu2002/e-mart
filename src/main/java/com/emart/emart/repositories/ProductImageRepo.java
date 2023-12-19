package com.emart.emart.repositories;

import com.emart.emart.models.Product;
import com.emart.emart.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    ProductImage findByImageIdAndDeletedIsFalse(Long imageId);
    List<ProductImage> findAllByProductAndDeletedIsFalse(Product product);
}
