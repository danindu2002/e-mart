package com.emart.emart.repositories.ref;

import com.emart.emart.models.ref.RefRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefRoleRepo extends JpaRepository<RefRole, Long> {

    RefRole findRefRoleByRefRoleId(Long refRoleId);
}
