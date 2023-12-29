package com.emart.emart.repositories.reference;


import com.emart.emart.models.reference.RefCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefCategoryRepo extends JpaRepository<RefCategory, Long> {

    RefCategory findByRefCategoryId(Long refCategoryId);
    RefCategory findByRefCategoryName(String name);

    //count categories
    Integer countAllBy();
    RefCategory findByCategoryCode(String categoryCode);

    @Query("SELECT r FROM RefCategory r WHERE (r.categoryCode LIKE %:keyword% OR r.categoryDescription LIKE %:keyword% OR r.refCategoryName LIKE %:keyword%)")
    List<RefCategory> searchCategories(@Param("keyword") String keyword);
}
