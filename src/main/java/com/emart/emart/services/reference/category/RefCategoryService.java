package com.emart.emart.services.reference.category;

import com.emart.emart.models.reference.RefCategory;

import java.util.List;

public interface RefCategoryService {
    int saveCategory(RefCategory category);
    RefCategory viewCategory(Long categoryId);
    List<RefCategory> viewAllCategories();
    int updateCategory(Long categoryId, RefCategory category);
    int deleteCategory(Long categoryId);

}
