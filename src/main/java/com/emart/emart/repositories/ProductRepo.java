package com.emart.emart.repositories;

import com.emart.emart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (p.productName LIKE %:keyword% OR p.description LIKE %:keyword%) AND p.deleted = false")
    List<Product> search(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE (p.productName LIKE %:keyword% OR p.category LIKE %:keyword%) AND p.deleted = false")
    List<Product> productSearchForAdmin(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <= :maxPrice AND p.deleted = false")
    List<Product> searchByPrice(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM Product p WHERE (p.price >= :minPrice AND p.price <= :maxPrice) AND p.category = :category AND p.deleted = false")
    List<Product> searchByPriceAndCategory(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("category") String category);

    List<Product> searchByCategoryAndDeletedIsFalse(@Param("category") String category);

    Product findByProductIdAndDeletedIsFalse(Long productId);
    Product findByProductCodeAndDeletedIsFalse(String productCode);
    List<Product> findAllByDeletedIsFalse();

    @Query("SELECT p.category AS category, COUNT(p) AS value FROM Product p WHERE p.deleted=false GROUP BY p.category")
    List<Object[]> getEachProductCategoryCount();
}
