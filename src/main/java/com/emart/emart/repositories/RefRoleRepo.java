package com.emart.emart.repositories;

import com.emart.emart.models.RefRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefRoleRepo extends JpaRepository<RefRole, Long> {

    RefRole findRefRoleByRefRoleId(Long refRoleId);
}
