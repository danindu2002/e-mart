package com.emart.emart.dtos.responseDtos;

import lombok.Data;

@Data
public class ResponseItemDto {
    private String status;
    private String description;
    private Object object;
}
