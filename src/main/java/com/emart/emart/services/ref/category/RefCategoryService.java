package com.emart.emart.services.ref.category;

import com.emart.emart.models.ref.RefCategory;

import java.util.List;

public interface RefCategoryService {
    int saveCategory(String categoryName);
    RefCategory viewCategory(Long categoryId);
    List<RefCategory> viewAllCategories();
    int updateCategory(Long categoryId, String categoryName);
    int deleteCategory(Long categoryId);

}
