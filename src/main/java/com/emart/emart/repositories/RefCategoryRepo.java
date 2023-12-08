package com.emart.emart.repositories;


import com.emart.emart.models.RefCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefCategoryRepo extends JpaRepository<RefCategory, Long> {

    RefCategory findByRefCategoryId(Long refCategoryId);
}
