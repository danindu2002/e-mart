package com.emart.emart.services.dashboard;

import com.emart.emart.dtos.*;

import java.util.List;

public interface DashboardService {

    int viewCustomerCount();

    int viewAdminCount();

    int viewCategoryCount();

    int viewDailyOrderCount();

    List<EachProductCategoryCountDto> viewProductCountForEachCategory();

    List<MonthlyIncomeDto> viewMonthlyIncome();

    List<TopCustomersDto> viewTopCustomers();
}
