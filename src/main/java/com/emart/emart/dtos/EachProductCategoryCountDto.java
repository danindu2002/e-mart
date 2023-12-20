package com.emart.emart.dtos;

import lombok.Data;

@Data
public class EachProductCategoryCountDto {
    private Integer id;
    private Long value;
    private String category;

    public EachProductCategoryCountDto(Integer id,Long value, String category) {
        this.id = id;
        this.value = value;
        this.category = category;
    }
}
