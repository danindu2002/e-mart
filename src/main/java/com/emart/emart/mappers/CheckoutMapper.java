package com.emart.emart.mappers;

import com.emart.emart.dtos.CheckoutDetailsDto;
import com.emart.emart.dtos.MonthlyIncomeDto;
import com.emart.emart.dtos.ProductDto;
import com.emart.emart.models.Checkout;
import com.emart.emart.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {
//    CheckoutMapper checkoutMapper = Mappers.getMapper(CheckoutMapper.class);
    CheckoutDetailsDto mapToCheckoutDetailsDto(Checkout checkout);
    List<Checkout> maptoCheckoutDetailsDtoList(List<Checkout> checkoutList);
    List<CheckoutDetailsDto> toDTOList(List<Checkout> checkoutList);
    MonthlyIncomeDto checkoutToMonthlyIncomeDTO(Checkout checkout);


}
