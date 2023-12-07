package com.emart.emart.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String address;
    private String role;
    private String profilePhoto;
}
