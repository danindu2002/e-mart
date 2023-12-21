//dashboard api
package com.emart.emart.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CheckoutDetailsDto {
    private Long checkoutId;
    private UserDashboardCheckoutDto user;
    private Date checkoutDate;
    private Double total;
}
