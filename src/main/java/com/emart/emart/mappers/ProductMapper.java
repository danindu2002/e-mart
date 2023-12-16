package com.emart.emart.mappers;

import com.emart.emart.dtos.ProductCheckoutDto;
import com.emart.emart.dtos.ProductDto;
import com.emart.emart.models.Product;
import com.emart.emart.models.ProductCheckout;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    // for general product details
    ProductDto mapToProductDto(Product product);
    List<ProductDto> maptoProductDtoList(List<Product> productList);

//    // for products at checkout
//    ProductCheckoutDto mapToProductCheckoutDto(Product product);
//    List<ProductCheckoutDto> maptoProductCheckoutDtoList(List<ProductCheckout> productList);

}
