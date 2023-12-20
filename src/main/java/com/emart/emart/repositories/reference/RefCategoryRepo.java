package com.emart.emart.repositories.reference;


import com.emart.emart.models.reference.RefCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefCategoryRepo extends JpaRepository<RefCategory, Long> {

    RefCategory findByRefCategoryId(Long refCategoryId);
    RefCategory findByRefCategoryName(String name);

    //count categories
    Integer countAllBy();
    RefCategory findByCategoryCode(String categoryCode);
}
