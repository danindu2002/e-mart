package com.emart.emart.dtos;

import lombok.Data;

@Data
public class TopCustomersDto {
    private Long userId;
    private String email;
    private String contactNo;
    private String firstName;
    private Double total;
    public TopCustomersDto(Long userId,String email, String contactNo,String firstName, Double total) {
        this.userId = userId;
        this.email = email;
        this.contactNo = contactNo;
        this.firstName = firstName;
        this.total = total;
    }
}
