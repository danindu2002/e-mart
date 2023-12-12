package com.emart.emart.controllers.ref.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RefCategoryController {
    ResponseEntity<Object> createCategory(@PathVariable String categoryName);
    ResponseEntity<Object> viewAllCategories();
    ResponseEntity<Object> updateCategory(@RequestParam String categoryName, @RequestParam Long categoryId);
    ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId);
}
