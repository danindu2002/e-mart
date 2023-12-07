package com.emart.emart.mappers;

import com.emart.emart.dtos.ProductDto;
import com.emart.emart.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    ProductDto mapToProductDto(Product product);
    List<ProductDto> maptoProductDtoList(List<Product> productList);
}
