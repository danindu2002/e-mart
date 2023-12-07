package com.emart.emart.repositories;

import com.emart.emart.dtos.UserDto;
import com.emart.emart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query(value = "select * from user where (first_name like %?1% or last_name like %?1% or email like %?1% or contact_no like %?1% or address like %?1%) and deleted = false and role = ?2", nativeQuery = true)
    List<User> searchUsers(String keyword, String role);

    @Query(value = "select * from user where deleted = false and role = ?1", nativeQuery = true)
    List<User> viewAllUsers(String role);

    @Query(value = "select role from user where email = ?1 and password = ?2 and deleted = false", nativeQuery = true)
    String authenticateUser(String email, String password);

    User findByEmailAndDeletedIsFalse(String email);
    User findByUserIdAndDeletedIsFalse(Long userId);
    List<User> findAllByDeletedIsFalse();
}
