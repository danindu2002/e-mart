package com.emart.emart.repositories;

import com.emart.emart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (u.firstName LIKE %:keyword% OR u.lastName LIKE %:keyword% OR u.email LIKE %:keyword% OR u.contactNo LIKE %:keyword% OR u.address LIKE %:keyword%) AND u.deleted = false AND u.role = :role")
    List<User> searchUsers(@Param("keyword") String keyword, @Param("role") String role);

    @Query("SELECT u FROM User u WHERE (u.deleted = false AND u.role = :role)")
    List<User> viewAllUsers(@Param("role") String role);

//    @Query("SELECT u.role FROM User u WHERE u.email = :email AND u.password = :password AND u.deleted = false")
//    String authenticateUser(@Param("email") String email, @Param("password") String password);


    User findByEmailAndDeletedIsFalse(String email);
    User findByUserIdAndDeletedIsFalse(Long userId);
    List<User> findAllByDeletedIsFalse();
}
