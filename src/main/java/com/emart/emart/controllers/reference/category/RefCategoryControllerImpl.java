package com.emart.emart.controllers.reference.category;

import com.emart.emart.models.reference.RefCategory;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.services.reference.category.RefCategoryService;
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
@RequestMapping("api/v1/categories")
public class RefCategoryControllerImpl implements RefCategoryController{

    private final Logger logger = LoggerFactory.getLogger(RefCategoryControllerImpl.class);

    @Autowired
    RefCategoryService refCategoryService;
    @Autowired
    private Utility utility;
    @Autowired
    UserRepo userRepo;

    @Override
    @PostMapping("/{userId}")
    public ResponseEntity<Object> createCategory(RefCategory refCategory, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            if (refCategoryService.saveCategory(refCategory) == 0) {
                logger.info("category created successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Category created successfully",  refCategoryService.viewCategory(refCategory.getRefCategoryId())));
            }
            else if (refCategoryService.saveCategory(refCategory) == 2) {
                logger.info("Duplicate category code found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(convertToResponseMsgDto("400 Bad Request", "Duplicate category code found"));
            }
            else throw new Exception();
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("Duplicate category name found", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Duplicate category name found"));
        }
    }

    @Override
    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> viewCategoryById(Long categoryId) {
        if(refCategoryService.viewCategory(categoryId) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Category found", refCategoryService.viewCategory(categoryId)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No categories found"));
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Object> viewAllCategories() {
        if(!refCategoryService.viewAllCategories().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "All categories found", refCategoryService.viewAllCategories()));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No categories found"));
    }

    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateCategory(RefCategory refCategory, Long categoryId, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            if(refCategoryService.updateCategory(categoryId, refCategory) == 0)
            {
                logger.info("Category updated successfully");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Category updated successfully",
                        refCategoryService.viewCategory(categoryId)));
            }
            else if(refCategoryService.updateCategory(categoryId, refCategory) == 2)
            {
                logger.info("Duplicate category name found");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseMsgDto("200 OK", "Duplicate category name found"));
            }
            else throw new Exception();
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("Category not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Category not found"));
        }
    }

    @Override
    @DeleteMapping("/{categoryId}/{userId}")
    public ResponseEntity<Object> deleteCategory(Long categoryId, Long userId) {
        try
        {
            if(utility.authorization(userId)) {
            if(refCategoryService.deleteCategory(categoryId) == 0) {
                logger.info("Category deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseMsgDto("200 OK", "Category deleted successfully"));
            }
            else if (refCategoryService.deleteCategory(categoryId) == 1) {
                logger.error("Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Category not found"));
            }
            else {
                logger.error("Unable to delete the category as there are products assigned to it");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized", "Unable to delete the category as there are products assigned to it"));
             }
            }
              else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error occurred");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Error occurred"));
        }
    }

    @GetMapping("/search-categories/{keyword}")
    @Override
    public ResponseEntity<Object> searchCategories(String keyword) {
        if(!refCategoryService.searchCategories(keyword).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "Searched categories found", refCategoryService.searchCategories(keyword)));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No categories found"));
    }
}
