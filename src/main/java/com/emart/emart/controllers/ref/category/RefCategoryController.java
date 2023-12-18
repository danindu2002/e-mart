package com.emart.emart.controllers.ref.category;

import com.emart.emart.models.ref.RefCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RefCategoryController {
    ResponseEntity<Object> createCategory(@RequestBody RefCategory refCategory);
    ResponseEntity<Object> viewAllCategories();
    ResponseEntity<Object> updateCategory(@RequestBody RefCategory refCategory, @RequestParam Long categoryId);
    ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId);
}
