package com.emart.emart.repositories;

import com.emart.emart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword% OR p.productCode LIKE %:keyword% OR p.description LIKE %:keyword% OR p.color = :keyword")
    List<Product> search(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE (p.productName LIKE %:keyword% OR p.productCode LIKE %:keyword% OR p.description LIKE %:keyword% OR p.color = :keyword) AND p.category = :category")
    List<Product> search(@Param("keyword") String keyword, @Param("category") String category);

    Product findByProductIdAndDeletedIsFalse(Long productId);
    List<Product> findAllByDeletedIsFalse();
}
