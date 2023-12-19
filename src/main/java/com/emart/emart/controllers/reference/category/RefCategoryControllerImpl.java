package com.emart.emart.controllers.reference.category;

import com.emart.emart.models.reference.RefCategory;
import com.emart.emart.services.reference.category.RefCategoryService;
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

    @Override
    @PostMapping("/")
    public ResponseEntity<Object> createCategory(RefCategory refCategory) {
        try
        {
            if (refCategoryService.saveCategory(refCategory) == 0) {
                logger.info("category created successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "category created successfully",  refCategoryService.viewCategory(refCategory.getRefCategoryId())));
            }
            else if (refCategoryService.saveCategory(refCategory) == 2) {
                logger.info("Duplicate category code found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(convertToResponseMsgDto("400 Bad Request", "Duplicate category code found"));
            }
            else throw new Exception();
        }
        catch (Exception e)
        {
            logger.error("Duplicate category name found", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Duplcate category name found"));
        }
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Object> viewAllCategories() {
        if(!refCategoryService.viewAllCategories().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseListDto("200 OK", "All categories found", refCategoryService.viewAllCategories()));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "no categories found"));
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<Object> updateCategory(RefCategory refCategory, Long categoryId) {
        try
        {
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
        }
        catch (Exception e)
        {
            logger.error("Category not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Category not found"));
        }
    }

    @Override
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(Long categoryId) {
        try
        {
            if(refCategoryService.deleteCategory(categoryId) == 0)
            {
                logger.info("Category deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseMsgDto("200 OK", "Category deleted successfully"));
            }
            else throw new Exception();
        }
        catch (Exception e)
        {
            logger.error("Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "Category not found"));
        }
    }
}
