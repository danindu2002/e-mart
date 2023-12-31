package com.emart.emart.services.product;

import com.emart.emart.dtos.productCheckoutDtos.ProductDto;
import com.emart.emart.models.Product;

import java.util.List;

public interface ProductService {
    void saveProduct(Product product);
    ProductDto viewProduct(Long productId);
    List<ProductDto> viewAllProducts();
    List<ProductDto> searchProducts(String keyword);
    List<ProductDto> searchProductsForAdmin(String keyword);
    List<ProductDto> searchByPrice(Double minPrice, Double maxPrice);
    List<ProductDto> searchByCategory(String category);
    List<ProductDto> searchByPriceAndCategory(String category, Double minPrice, Double maxPrice);
    int updateProduct(Long productId, Product product);
    int deleteProduct(Long productId);

//    void deleteDocumentFile(String filePath);
}
