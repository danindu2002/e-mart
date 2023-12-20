//dashboard api
package com.emart.emart.dtos;

import com.emart.emart.models.User;
import lombok.Data;

import java.util.Date;

@Data
public class CheckoutDetailsDto {
//    checkout id
//    user profile photo
//    user firstname
//    date
//            total
    private Long checkoutId;
    private UserDashboardCheckoutDto user;
    private Date checkoutDate;
    private Double total;
}
