package com.emart.emart.controllers.product;

import com.emart.emart.controllers.user.UserControllerImpl;
import com.emart.emart.models.Product;
import com.emart.emart.repositories.ProductRepo;
import com.emart.emart.services.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emart.emart.utility.Utility.convertToResponseItemDto;
import static com.emart.emart.utility.Utility.convertToResponseMsgDto;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductControllerImpl implements ProductController{
    private final Logger logger = LoggerFactory.getLogger(ProductControllerImpl.class);

    @Autowired
    private ProductService productService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(Product product) {
        try
        {
            productService.saveProduct(product);
            logger.info("product created successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertToResponseItemDto("200 OK", "product created successfully",productService.viewProduct(product.getProductId())));
        }
        catch (Exception e)
        {
            logger.error("Failed to create the product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Failed to create the product"));
        }
    }

    @Override
    @GetMapping("/viewAll")
    public ResponseEntity<Object> viewAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<Object> searchProducts(String keyword) {
        return null;
    }

    @Override
    public ResponseEntity<Object> searchByPrice(Double minPrice, Double maxPrice) {
        return null;
    }

    @Override
    public ResponseEntity<Object> searchByCategory(String category) {
        return null;
    }

    @Override
    public ResponseEntity<Object> searchByPriceAndCategory(Double minPrice, Double maxPrice, String category) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long productId) {
        return null;
    }
}
