package com.emart.emart.controllers.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DashboardController {

    ResponseEntity<Object> viewCustomerCount(@PathVariable Long userId);
    ResponseEntity<Object> viewAdminCount(@PathVariable Long userId);
    ResponseEntity<Object> viewCategoryCount(@PathVariable Long userId);
    ResponseEntity<Object> viewDailyOrderCount(@PathVariable Long userId);
    ResponseEntity<Object> viewProductCountForEachCategory(@PathVariable Long userId);
    ResponseEntity<Object> viewMonthlyIncome(@PathVariable Long userId);
    ResponseEntity<Object> viewTopCustomers(@PathVariable Long userId);
}
