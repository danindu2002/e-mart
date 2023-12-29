package com.emart.emart.services.reference.category;

import com.emart.emart.models.reference.RefCategory;
import com.emart.emart.repositories.ProductRepo;
import com.emart.emart.repositories.reference.RefCategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefCategoryServiceImpl implements RefCategoryService {

    private final Logger logger = LoggerFactory.getLogger(RefCategoryServiceImpl.class);

    @Autowired
    RefCategoryRepo refCategoryRepo;
    @Autowired
    ProductRepo productRepo;
    @Override
    public int saveCategory(RefCategory category) {
        RefCategory category1 = refCategoryRepo.findByRefCategoryName(category.getRefCategoryName());
        RefCategory categoryByCode = refCategoryRepo.findByCategoryCode(category.getCategoryCode());

        if (category1 != null) return 1; // duplicate category
        else if (categoryByCode != null) return 2; // duplicate category code
        else {
            if (!category.getRefCategoryName().trim().isBlank() && !category.getCategoryCode().trim().isBlank()) {
                refCategoryRepo.save(category);
                logger.info("category saved");
                return 0; // saved
            }
            else {
                logger.error("empty input");
                return 3;
            }
        }
    }

    @Override
    public RefCategory viewCategory(Long categoryId) {
        logger.info("fetched category");
        return refCategoryRepo.findByRefCategoryId(categoryId);
    }

    @Override
    public List<RefCategory> viewAllCategories() {
        logger.info("fetched all categories");
        return refCategoryRepo.findAll();
    }

    @Override
    public int updateCategory(Long categoryId, RefCategory category) {
        RefCategory category1 = refCategoryRepo.findByRefCategoryId(categoryId);
        RefCategory categoryByName = refCategoryRepo.findByRefCategoryName(category.getRefCategoryName());

        if (category1 == null) return 1; // category not found
        else if(categoryByName != null && !categoryId.equals(categoryByName.getRefCategoryId())) return 2; // duplicate name
        else {
            if (!category.getRefCategoryName().trim().isBlank()) {
                category1.setRefCategoryName(category.getRefCategoryName());
                category1.setCategoryDescription(category.getCategoryDescription());
                refCategoryRepo.save(category1);

                logger.info("category updated");
                return 0; // updated
            }
            else {
                logger.error("empty input");
                return 3;
            }
        }
    }

    @Override
    public int deleteCategory(Long categoryId) {
        RefCategory category = refCategoryRepo.findByRefCategoryId(categoryId);
        if (category == null) return 1;
        else {
            if (!productRepo.searchByCategoryAndDeletedIsFalse(category.getRefCategoryName()).isEmpty()) {
                logger.error("Unable to delete the category as there are products assigned to it");
                return 2;
            }
            else {
                logger.info("category deleted");
                refCategoryRepo.delete(category);
                return 0;
            }
        }
    }

    public List<RefCategory> searchCategories(String keyword) {
        logger.info("categories searched globally");
        return refCategoryRepo.searchCategories(keyword);
    }
}
