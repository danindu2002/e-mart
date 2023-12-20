package com.emart.emart.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MonthlyIncomeDto {
    private Double value;
    private String month;

    public MonthlyIncomeDto(Double value, String month) {
        this.value = value;
        this.month = month;
    }
}
