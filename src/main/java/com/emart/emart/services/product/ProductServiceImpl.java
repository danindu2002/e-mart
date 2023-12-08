package com.emart.emart.services.product;

import com.emart.emart.dtos.ProductDto;
import com.emart.emart.mappers.ProductMapper;
import com.emart.emart.models.Product;
import com.emart.emart.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepo productRepo;

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public ProductDto viewProduct(Long productId) {
        return ProductMapper.productMapper.mapToProductDto(productRepo.findByProductIdAndDeletedIsFalse(productId));
    }

    @Override
    public List<ProductDto> viewAllProducts() {
        return ProductMapper.productMapper.maptoProductDtoList(productRepo.findAllByDeletedIsFalse());
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
