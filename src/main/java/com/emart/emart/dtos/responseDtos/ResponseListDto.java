package com.emart.emart.dtos.responseDtos;

import lombok.Data;

import java.util.List;
@Data
public class ResponseListDto {
    private String status;
    private String description;
    private List<?> responseList;
}
