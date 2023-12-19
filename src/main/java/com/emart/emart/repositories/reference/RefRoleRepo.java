package com.emart.emart.repositories.reference;

import com.emart.emart.models.reference.RefRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefRoleRepo extends JpaRepository<RefRole, Long> {

    RefRole findRefRoleByRefRoleId(Long refRoleId);
}
