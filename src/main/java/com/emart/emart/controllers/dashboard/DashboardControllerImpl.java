package com.emart.emart.controllers.dashboard;

import com.emart.emart.controllers.user.UserControllerImpl;
import com.emart.emart.dtos.EachProductCategoryCountDto;
import com.emart.emart.dtos.MonthlyIncomeDto;
import com.emart.emart.dtos.TopCustomersDto;
import com.emart.emart.services.dashboard.DashboardService;
import com.emart.emart.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private Utility utility;

    @GetMapping("/customer-count/{userId}")
    @Override
    public ResponseEntity<Object> viewCustomerCount(Long userId) {
        if (utility.authorization(userId)) {
            int customerCount = dashboardService.viewCustomerCount();
            if (customerCount != -1) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "All customer count fetched", customerCount));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @GetMapping("/admin-count/{userId}")
    @Override
    public ResponseEntity<Object> viewAdminCount(Long userId) {
        if (utility.authorization(userId)) {
            int adminCount = dashboardService.viewAdminCount();
            if (adminCount != -1) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "All admin count fetched", adminCount));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @GetMapping("/category-count/{userId}")
    @Override
    public ResponseEntity<Object> viewCategoryCount(Long userId) {
        if (utility.authorization(userId)) {
            int categoryCount = dashboardService.viewCategoryCount();
            if (categoryCount != -1) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "All category count fetched", categoryCount));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @GetMapping("/order-count/{userId}")
    @Override
    public ResponseEntity<Object> viewDailyOrderCount(Long userId) {
        if (utility.authorization(userId)) {
            int dailyOrderCount = dashboardService.viewDailyOrderCount();
            if (dailyOrderCount != -1) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Daily order count fetched", dailyOrderCount));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @GetMapping("/category-product-count/{userId}")
    @Override
    public ResponseEntity<Object> viewProductCountForEachCategory(Long userId) {
        if (utility.authorization(userId)) {
            List<EachProductCategoryCountDto> productCountForEachCategory = dashboardService.viewProductCountForEachCategory();
            if (productCountForEachCategory != null) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Each category product count fetched", productCountForEachCategory));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @GetMapping("/monthly-income/{userId}")
    @Override
    public ResponseEntity<Object> viewMonthlyIncome(Long userId) {
        if (utility.authorization(userId)) {
            List<MonthlyIncomeDto> monthlyIncome = dashboardService.viewMonthlyIncome();
            if (monthlyIncome != null) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "fetched monthly income", monthlyIncome));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }

    @GetMapping("/checkout/{userId}")
    @Override
    public ResponseEntity<Object> viewTopCustomers(Long userId) {
        if (utility.authorization(userId)) {
            List<TopCustomersDto> topCustomers = dashboardService.viewTopCustomers();
            if (topCustomers != null) {
                return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Top customers details fetched", topCustomers));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(convertToResponseMsgDto("500 Internal Server Error", "Unexpected error occurred"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(convertToResponseMsgDto("401 Unauthorized Access", "Unauthorized Access"));
        }
    }
}
