package com.emart.emart.services.product;

import com.emart.emart.dtos.ProductDto;
import com.emart.emart.models.Product;

import java.util.List;

public interface ProductService {
    void saveProduct(Product product);
    ProductDto viewProduct(Long productId);
    List<ProductDto> viewAllProducts();
    List<ProductDto> searchProducts(String keyword);
    List<ProductDto> searchProductsByCategory(String category);
    int updateProduct(Long productId, Product product);
    int deleteProduct(Long productId);
}
