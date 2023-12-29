package com.emart.emart.controllers.product;

import com.emart.emart.dtos.productCheckoutDtos.ProductDto;
import com.emart.emart.models.Product;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.services.product.ProductService;
import com.emart.emart.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emart.emart.utility.Utility.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/products")
public class ProductControllerImpl implements ProductController{
    private final Logger logger = LoggerFactory.getLogger(ProductControllerImpl.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private Utility utility;

    @Override
    @PostMapping("/{userId}")
    public ResponseEntity<Object> createProduct(Product product, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            productService.saveProduct(product);
            logger.info("product created successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertToResponseItemDto("200 OK", "Product created successfully",productService.viewProduct(product.getProductId())));
        }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("Duplicate product code found",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Duplicate product code found"));
        }
        }

    @Override
    @GetMapping("/view-products")
    public ResponseEntity<Object> viewAllProducts() {
        if(!productService.viewAllProducts().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "All products found", productService.viewAllProducts()));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No products found"));
    }

    @Override
    @GetMapping("/view-products/{productId}")
    public ResponseEntity<Object> viewById(Long productId) {
       try {
            ProductDto product = productService.viewProduct(productId);
            if (product == null) throw new Exception();
            else {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Product found", product));
            }
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No products found"));
       }
    }

    @Override
    @GetMapping("/search-products/{keyword}")
    public ResponseEntity<Object> searchProducts(String keyword) {
        if(!productService.searchProducts(keyword).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Searched products found", productService.searchProducts(keyword)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No products found"));
    }

    @Override
    @GetMapping("/search-price")
    public ResponseEntity<Object> searchByPrice(Double minPrice, Double maxPrice) {
        if(!productService.searchByPrice(minPrice, maxPrice).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Searched products found", productService.searchByPrice(minPrice, maxPrice)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No products found"));
    }

    @Override
    @GetMapping("/search-category/{category}")
    public ResponseEntity<Object> searchByCategory(String category) {
        if(!productService.searchByCategory(category).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Searched products found", productService.searchByCategory(category)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No products found"));
    }

    @Override
    @GetMapping("/search-both")
    public ResponseEntity<Object> searchByPriceAndCategory(Double minPrice, Double maxPrice, String category) {
        if(!productService.searchByPriceAndCategory(category, minPrice, maxPrice).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Searched products found", productService.searchByPriceAndCategory(category, minPrice, maxPrice)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No products found"));
    }

    @Override
    @PutMapping("/{productId}/{userId}")
    public ResponseEntity<Object> updateProduct(Long productId, Product product, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            if(productService.updateProduct(productId, product) == 0)
            {
                logger.info("Product updated successfully");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Product updated successfully",
                        productService.viewProduct(productId)));
            }
            else if(productService.updateProduct(productId, product) == 1)
            {
                logger.info("Duplicate product code found");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 Not Acceptable", "Duplicate product code found, please try again"));
            }
            else throw new Exception();
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Product not found"));
        }
    }

    @Override
    @DeleteMapping("/{productId}/{userId}")
    public ResponseEntity<Object> deleteProduct(Long productId, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            if (productService.deleteProduct(productId) == 0)
            {
                logger.info("Product deleted");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseMsgDto("200 OK", "Product deleted successfully"));
            }
            else
            {
                logger.error("Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Product not found"));
            }
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("An error occurred while deleting the product");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "An error occurred while deleting the product"));
        }
    }
}
