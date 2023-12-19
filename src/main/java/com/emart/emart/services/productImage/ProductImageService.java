package com.emart.emart.services.productImage;

import com.emart.emart.dtos.productImageDtos.ProductImageDetailsDto;
import com.emart.emart.dtos.productImageDtos.ProductImageDto;
import com.emart.emart.models.ProductImage;

import java.util.List;

public interface ProductImageService {
    int saveProductImage(ProductImageDto productImageDto);
    int deleteImage(Long imageId);
    List<ProductImageDetailsDto> viewAllImages(Long productId);
    ProductImageDto viewImage(Long imageId);
    ProductImageDto mapToImageDto(ProductImage productImage);
    ProductImageDetailsDto mapToImageDetailsDto(ProductImage productImage);
    List<ProductImageDetailsDto> mapToImageDetailsDtoList(List<ProductImage> productImages);
}
