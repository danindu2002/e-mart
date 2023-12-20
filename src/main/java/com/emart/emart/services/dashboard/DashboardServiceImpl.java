package com.emart.emart.services.dashboard;

import com.emart.emart.dtos.*;
import com.emart.emart.mappers.CheckoutMapper;
import com.emart.emart.models.Checkout;
import com.emart.emart.repositories.CheckoutRepo;
import com.emart.emart.repositories.ProductRepo;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.repositories.reference.RefCategoryRepo;
import com.emart.emart.services.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RefCategoryRepo refCategoryRepo;
    @Autowired
    private CheckoutRepo checkoutRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CheckoutMapper checkoutMapper;

    @Override
    public int viewCustomerCount() {
        try {
            logger.info("fetched all customer count");
            return userRepo.countAllByDeletedIsFalseAndRoleIsContainingIgnoreCase("user");
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewCustomerCount", e);
            return -1;
        }
    }

    @Override
    public int viewAdminCount() {
        try {
            logger.info("fetched all admin count");
            return userRepo.countAllByDeletedIsFalseAndRoleIsContainingIgnoreCase("admin");
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewAdminCount", e);
            return -1;
        }
    }

    @Override
    public int viewCategoryCount() {
        try {
            logger.info("fetched all category count");
            return refCategoryRepo.countAllBy();
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewCategoryCount", e);
            return -1;
        }
    }

    @Override
    public int viewDailyOrderCount() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneDayAgo = now.minusDays(1);
            Date oneDayAgoDate = Date.from(oneDayAgo.atZone(ZoneId.systemDefault()).toInstant());

            logger.info("fetched daily order count");
            return checkoutRepo.getDailyOrderCount(oneDayAgoDate);
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewDailyOrderCount", e);
            return -1;
        }
    }

    @Override
    public List<EachProductCategoryCountDto> viewProductCountForEachCategory() {
        try {
            List<Object[]> eachProdCatCount = productRepo.getEachProductCategoryCount();
            List<EachProductCategoryCountDto> result = new ArrayList<>();

            int id = 0;
            for (Object[] obj : eachProdCatCount) {
                Long value = (Long) obj[1];
                String category = (String) obj[0];
                id++;

                result.add(new EachProductCategoryCountDto(id, value, category));
            }
            return result;
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewProductCountForEachCategory", e);
            return null;
        }
    }

    @Override
    public List<MonthlyIncomeDto> viewMonthlyIncome() {
        try {
            List<Integer> months = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            List<Object[]> monthlyTotals = checkoutRepo.getMonthlyTotals(months);
            List<MonthlyIncomeDto> result = new ArrayList<>();

            for (Object[] obj : monthlyTotals) {
                Integer month = (Integer) obj[0];
                Double sumTotal = (Double) obj[1];

                result.add(new MonthlyIncomeDto(sumTotal, Month.of(month).name()));
            }
            logger.info("monthly income processed and returned");
            return result;
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewMonthlyIncome", e);
            return null;
        }
    }

    @Override
    public List<CheckoutDetailsDto> viewCheckoutDetails() {
        try {
            logger.info("fetched checkout details");
            return checkoutMapper.toDTOList(checkoutRepo.findAllByOrderedIsTrue());
        } catch (Exception e) {
            logger.error("unexpected error occurred in viewCheckoutDetails", e);
            return null;
        }
    }
}
