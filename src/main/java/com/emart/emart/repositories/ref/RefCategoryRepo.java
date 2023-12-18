package com.emart.emart.repositories.ref;


import com.emart.emart.models.ref.RefCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefCategoryRepo extends JpaRepository<RefCategory, Long> {

    RefCategory findByRefCategoryId(Long refCategoryId);
    RefCategory findByRefCategoryName(String name);
    RefCategory findByCategoryCode(String categoryCode);
}
