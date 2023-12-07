package com.emart.emart.services.product;

import com.emart.emart.dtos.ProductDto;
import com.emart.emart.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public int saveProduct(Product product) {
        return 0;
    }

    @Override
    public ProductDto viewProduct(Long productId) {
        return null;
    }

    @Override
    public List<ProductDto> viewAllProducts() {
        return null;
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        return null;
    }

    @Override
    public List<ProductDto> searchProductsByCategory(String category) {
        return null;
    }

    @Override
    public int updateProduct(Long productId, Product product) {
        return 0;
    }

    @Override
    public int deleteProduct(Long productId) {
        return 0;
    }
}
