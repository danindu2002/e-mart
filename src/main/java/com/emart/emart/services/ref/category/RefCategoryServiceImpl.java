package com.emart.emart.services.ref.category;

import com.emart.emart.models.ref.RefCategory;
import com.emart.emart.repositories.ref.RefCategoryRepo;
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
    @Override
    public int saveCategory(String categoryName) {
        RefCategory category = refCategoryRepo.findByRefCategoryName(categoryName);
        if(category != null) return 1;
        else {
            category = new RefCategory();
            category.setRefCategoryName(categoryName);
            logger.info("category saved");
            refCategoryRepo.save(category);
            return 0;
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
    public int updateCategory(Long categoryId, String categoryName) {
        RefCategory category = refCategoryRepo.findByRefCategoryId(categoryId);
        RefCategory categoryByName = refCategoryRepo.findByRefCategoryName(categoryName);

        if (category == null) return 1; // category not found
        else if(categoryByName != null) return 2; // duplicate name
        else {
            category.setRefCategoryName(categoryName);
            logger.info("category updated");
            refCategoryRepo.save(category);
            return 0; // updated
        }
    }

    @Override
    public int deleteCategory(Long categoryId) {
        RefCategory category = refCategoryRepo.findByRefCategoryId(categoryId);
        if (category == null) return 1;
        else {
            logger.info("category deleted");
            refCategoryRepo.delete(category);
            return 0;
        }
    }
}
