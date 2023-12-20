package com.emart.emart.services.dashboard;

import com.emart.emart.dtos.CheckoutDetailsDto;
import com.emart.emart.dtos.EachProductCategoryCountDto;
import com.emart.emart.dtos.MonthlyIncomeDto;
import com.emart.emart.dtos.UserDashboardCheckoutDto;
import com.emart.emart.models.Checkout;
import com.emart.emart.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DashboardService {

    int viewCustomerCount();

    int viewAdminCount();

    int viewCategoryCount();

    int viewDailyOrderCount();

    List<EachProductCategoryCountDto> viewProductCountForEachCategory();

    List<MonthlyIncomeDto> viewMonthlyIncome();

    List<CheckoutDetailsDto> viewCheckoutDetails();
}
