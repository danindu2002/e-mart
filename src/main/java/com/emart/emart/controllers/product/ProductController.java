package com.emart.emart.controllers.product;

import com.emart.emart.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductController{
    ResponseEntity<Object> createProduct(@RequestBody Product product, @PathVariable Long userId);
    ResponseEntity<Object> viewAllProducts();
    ResponseEntity<Object> viewById(@PathVariable Long productId);
    ResponseEntity<Object> viewByIdAndCategory(@PathVariable Long productId);
    ResponseEntity<Object> searchProducts(@PathVariable String keyword);
    ResponseEntity<Object> searchProductsForAdmin(@PathVariable String keyword);
    ResponseEntity<Object> searchByPrice(@RequestParam Double minPrice, @RequestParam Double maxPrice);
    ResponseEntity<Object> searchByCategory(@PathVariable String category);
    ResponseEntity<Object> searchByPriceAndCategory(@RequestParam Double minPrice, @RequestParam Double maxPrice, @RequestParam String category);
    ResponseEntity<Object> updateProduct(@PathVariable Long productId, @RequestBody Product product, @PathVariable Long userId);
    ResponseEntity<Object> deleteProduct(@PathVariable Long productId, @PathVariable Long userId);
}
