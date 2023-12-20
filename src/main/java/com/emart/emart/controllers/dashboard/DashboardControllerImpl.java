package com.emart.emart.controllers.dashboard;

import com.emart.emart.controllers.user.UserControllerImpl;
import com.emart.emart.dtos.CheckoutDetailsDto;
import com.emart.emart.dtos.EachProductCategoryCountDto;
import com.emart.emart.dtos.MonthlyIncomeDto;
import com.emart.emart.models.Checkout;
import com.emart.emart.models.User;
import com.emart.emart.services.dashboard.DashboardService;
import com.emart.emart.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.emart.emart.utility.Utility.convertToResponseItemDto;
import static com.emart.emart.utility.Utility.convertToResponseMsgDto;

@RestController
@CrossOrigin
@RequestMapping("api/v1/dashboard")
public class DashboardControllerImpl implements DashboardController {

    private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/customer-count")
    @Override
    public ResponseEntity<Object> viewCustomerCount() {
        int customerCount=dashboardService.viewCustomerCount();
        if(customerCount!=-1) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "All customer count fetched", customerCount));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
        }
    }

    @GetMapping("/admin-count")
    @Override
    public ResponseEntity<Object> viewAdminCount() {
        int adminCount=dashboardService.viewAdminCount();
        if(adminCount!=-1) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "All admin count fetched", adminCount));
    }else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
    }
    }

    @GetMapping("/category-count")
    @Override
    public ResponseEntity<Object> viewCategoryCount() {
        int categoryCount=dashboardService.viewCategoryCount();
        if(categoryCount!=-1) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "All category count fetched", categoryCount));
    }else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
        }
}

    @GetMapping("/order-count")
    @Override
    public ResponseEntity<Object> viewDailyOrderCount() {
        int dailyOrderCount=dashboardService.viewDailyOrderCount();
        if(dailyOrderCount!=-1) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Daily order count fetched", dailyOrderCount));
    }else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
    }
    }

    @GetMapping("/category-product-count")
    @Override
    public ResponseEntity<Object> viewProductCountForEachCategory() {
        List<EachProductCategoryCountDto> productCountForEachCategory=dashboardService.viewProductCountForEachCategory();
        if(productCountForEachCategory!=null) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Each category product count fetched", productCountForEachCategory));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
        }
        }

    @GetMapping("/monthly-income")
    @Override
    public ResponseEntity<Object> viewMonthlyIncome() {
        List<MonthlyIncomeDto> monthlyIncome=dashboardService.viewMonthlyIncome();
        if(monthlyIncome!=null) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "fetched monthly income", monthlyIncome));
    }else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
    }
    }

    @GetMapping("/checkout")
    @Override
    public ResponseEntity<Object> viewCheckoutDetails() {
        List<CheckoutDetailsDto> checkoutDetails=dashboardService.viewCheckoutDetails();
        if(checkoutDetails!=null) {
        return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Checkout details fetched", checkoutDetails));
    }else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
        }
}
}
