package com.emart.emart.controllers.productImage;

import com.emart.emart.dtos.productImageDtos.ProductImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProductImageController {
    ResponseEntity<Object> saveImage(@RequestBody ProductImageDto productImageDto);
    ResponseEntity<Object> deleteImage(@RequestParam Long imageId);
    ResponseEntity<Object> viewAllImageDetailsByProductId(@RequestParam Long productId);
    ResponseEntity<Object> viewAllImagesByProductId(@RequestParam Long productId);
    ResponseEntity<Object> viewImage(@RequestParam Long imageId);
}
