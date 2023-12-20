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

    List<Checkout> findAllByOrderedIsTrue();

    @Query("SELECT MONTH(c.checkoutDate) AS month, SUM(c.total) AS sumTotal " +
            "FROM Checkout c " +
            "WHERE c.ordered = true AND MONTH(c.checkoutDate) IN :months " +
            "GROUP BY MONTH(c.checkoutDate)")
    List<Object[]> getMonthlyTotals(@Param("months") List<Integer> months);

}
