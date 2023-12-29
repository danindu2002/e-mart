package com.emart.emart.controllers.reference.category;

import com.emart.emart.models.reference.RefCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RefCategoryController {
    ResponseEntity<Object> createCategory(@RequestBody RefCategory refCategory, @PathVariable Long userId);
    ResponseEntity<Object> viewAllCategories();
    ResponseEntity<Object> updateCategory(@RequestBody RefCategory refCategory, @RequestParam Long categoryId, @PathVariable Long userId);
    ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId, @PathVariable Long userId);
    ResponseEntity<Object> searchCategories(@PathVariable String keyword);
}
