package com.emart.emart.controllers.productImage;

import com.emart.emart.dtos.productImageDtos.ProductImageDetailsDto;
import com.emart.emart.dtos.productImageDtos.ProductImageDto;
import com.emart.emart.models.ProductImage;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.services.productImage.ProductImageService;
import com.emart.emart.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.emart.emart.utility.Utility.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ProductImageControllerImpl implements ProductImageController {

    private final Logger logger = LoggerFactory.getLogger(ProductImageControllerImpl.class);

    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private Utility utility;

    @Override
    @PostMapping("/{userId}")
    public ResponseEntity<Object> saveImage(ProductImageDto productImageDto, Long userId) {
        try
        {

            if(!utility.authorization(userId)) {
            if (productImageService.saveProductImage(productImageDto) == 0){
                logger.info("image saved successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseMsgDto("200 OK", "Image saved successfully"));
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
            }
            else if (productImageService.saveProductImage(productImageDto) == 1) {
                logger.error("Invalid image format. Only image files are allowed");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(convertToResponseMsgDto("406 NOT ACCEPTABLE", "Invalid image format. Only image files are allowed"));
            }
            else {
                logger.error("Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 NOT FOUND", "Product not found"));

            }
        }
        catch (Exception e)
        {
            logger.error("Error saving the image",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error saving the image"));
        }
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteImage(Long imageId, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            if (productImageService.deleteImage(imageId) == 0){
                logger.info("image deleted successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseMsgDto("200 OK", "Image deleted successfully"));
            }
            else {
                logger.info("image not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 NOT FOUND", "Image not found"));
            }
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error deleting the image",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error deleting the image"));
        }
    }

    @Override
    @GetMapping("/all-image-details")
    public ResponseEntity<Object> viewAllImageDetailsByProductId(Long productId) {
        try
        {
            List<ProductImageDetailsDto> list = productImageService.viewAllImageDetails(productId);
            if (!list.isEmpty()){
                logger.info("Image details list fetched successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseListDto("200 OK", "Image details list fetched successfully", list));
            }
            else {
                List<ProductImageDetailsDto> emptyList = new ArrayList<>();
                logger.info("No images found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseListDto("404 NOT FOUND", "No images found", emptyList));
            }
        }
        catch (Exception e)
        {
            logger.error("Error fetching image details",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error fetching image details"));
        }
    }

    @Override
    @GetMapping("/all-images")
    public ResponseEntity<Object> viewAllImagesByProductId(Long productId) {
        try
        {
            List<ProductImageDto> list = productImageService.viewAllImages(productId);
            if (!list.isEmpty()){
                logger.info("Images fetched successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseListDto("200 OK", "Images fetched successfully", list));
            }
            else {
                List<ProductImageDetailsDto> emptyList = new ArrayList<>();
                logger.info("No images found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseListDto("404 NOT FOUND", "No images found", emptyList));
            }
        }
        catch (Exception e)
        {
            logger.error("Error fetching image details",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error fetching image details"));
        }
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Object> viewImage(Long imageId) {
        try
        {
            ProductImageDto productImageDto = productImageService.viewImage(imageId);
            if (productImageDto != null){
                logger.info("Image fetched successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Image fetched successfully", productImageDto));
            }
            else {
                logger.info("No image found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 NOT FOUND", "No image found"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error fetching the image",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error fetching the image"));
        }
    }
}
