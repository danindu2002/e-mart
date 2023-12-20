package com.emart.emart.controllers.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DashboardController {

    ResponseEntity<Object> viewCustomerCount();
    ResponseEntity<Object> viewAdminCount();
    ResponseEntity<Object> viewCategoryCount();
    ResponseEntity<Object> viewDailyOrderCount();
    ResponseEntity<Object> viewProductCountForEachCategory();
    ResponseEntity<Object> viewMonthlyIncome();
    ResponseEntity<Object> viewCheckoutDetails();
}
