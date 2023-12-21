package com.emart.emart.repositories;

import com.emart.emart.models.Checkout;
import com.emart.emart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout, Long> {
    Checkout findByUserAndOrderedIsFalse(User user);
    Checkout findByCheckoutId(Long checkoutId);

    @Query("SELECT COUNT(c) FROM Checkout c WHERE c.checkoutDate >= :date")
    Integer getDailyOrderCount(@Param("date") Date date);

    @Query("SELECT u.userId, u.email, u.contactNo, u.firstName, SUM(c.total) AS totalOrdered " +
            "FROM User u " +
            "JOIN Checkout c ON u.userId = c.user.userId " +
            "WHERE c.ordered = true " +
            "GROUP BY u.userId, u.email, u.contactNo, u.firstName " +
            "ORDER BY totalOrdered DESC")
    List<Object[]> getTopCustomers();

//    @Query("SELECT MONTH(c.checkoutDate) AS month, SUM(c.total) AS sumTotal " +
//            "FROM Checkout c " +
//            "WHERE c.ordered = true AND MONTH(c.checkoutDate) IN :months " +
//            "GROUP BY MONTH(c.checkoutDate)")

    @Query("SELECT MONTH(c.checkoutDate) AS month, SUM(c.total) AS sumTotal " +
            "FROM Checkout c " +
            "WHERE c.ordered = true AND YEAR(c.checkoutDate) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(c.checkoutDate)")
    List<Object[]> getMonthlyTotals();

}
