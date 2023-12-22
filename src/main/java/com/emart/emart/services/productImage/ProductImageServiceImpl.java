package com.emart.emart.services.productImage;

import com.emart.emart.dtos.productImageDtos.ProductImageDetailsDto;
import com.emart.emart.dtos.productImageDtos.ProductImageDto;
import com.emart.emart.models.Product;
import com.emart.emart.models.ProductImage;
import com.emart.emart.repositories.ProductImageRepo;
import com.emart.emart.repositories.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.emart.emart.utility.Utility.convertDocumentToBase64;
import static com.emart.emart.utility.Utility.saveBase64DocumentToFile;

@Service
public class ProductImageServiceImpl implements ProductImageService{

    private final Logger logger = LoggerFactory.getLogger(ProductImageServiceImpl.class);

    @Value("${baseUrl.fileLocation}")
    private String baseFileLocation;

    @Autowired
    ProductImageRepo productImageRepo;
    @Autowired
    ProductRepo productRepo;

    // saving a product image
    @Override
    public int saveProductImage(ProductImageDto productImageDto) {
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productImageDto.getProductId());

        if (product != null) {
            // Saving the image
            String imageName = productImageDto.getImageName() + "_" + System.currentTimeMillis() + ".jpg";

            // Change the base file location from yml file
            String imageFilePath = baseFileLocation + "\\images\\" + product.getProductCode() + "\\" + imageName;
            saveBase64DocumentToFile(productImageDto.getImage(), imageFilePath);

            ProductImage productImage = new ProductImage();
            productImage.setImageName(productImageDto.getImageName());
            productImage.setImagePath(imageFilePath);
            productImage.setProduct(product);
            productImageRepo.save(productImage);

            logger.info("image saved");
            return 0;
        }
        else {
            logger.error("product not found");
            return 1;
        }
    }

    // deleting a product image
    @Override
    public int deleteImage(Long imageId) {
        ProductImage productImage = productImageRepo.findByImageIdAndDeletedIsFalse(imageId);
        if (productImage != null) {
            productImage.setDeleted(true);
            productImageRepo.save(productImage);

            logger.info("image marked as deleted");
            return 0;
        }
        else {
            logger.info("image not found");
            return 1;
        }
    }

    // fetching all image details for a product
    @Override
    public List<ProductImageDetailsDto> viewAllImageDetails(Long productId) {
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productId);
        logger.info("fetched image details list");
        return mapToImageDetailsDtoList(productImageRepo.findAllByProductAndDeletedIsFalse(product));
    }

    // fetching all images for a product
    @Override
    public List<ProductImageDto> viewAllImages(Long productId) {
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productId);
        List<ProductImage> productImageObjects = productImageRepo.findAllByProductAndDeletedIsFalse(product);

        List<ProductImageDto> imagesList = new ArrayList<>();

        for ( ProductImage productImage : productImageObjects) {
            imagesList.add(mapToImageDto(productImage));
        }
        return imagesList;
    }

    // fetching an image
    @Override
    public ProductImageDto viewImage(Long imageId) {
        ProductImage productImage = productImageRepo.findByImageIdAndDeletedIsFalse(imageId);
        if (productImage != null) {
            logger.info("image fetched");
            return mapToImageDto(productImage);
        }
        else return null;
    }


    // helper methods for mapping custom DTOs
    @Override
    public ProductImageDto mapToImageDto(ProductImage productImage) {
        ProductImageDto dto = new ProductImageDto();
        dto.setImageId(productImage.getImageId());
        dto.setImageName(productImage.getImageName());
        dto.setImage(convertDocumentToBase64(productImage.getImagePath()));
        dto.setProductId(productImage.getProduct().getProductId());
        return dto;
    }

    @Override
    public ProductImageDetailsDto mapToImageDetailsDto(ProductImage productImage) {
        ProductImageDetailsDto dto = new ProductImageDetailsDto();
        dto.setImageName(productImage.getImageName());
        dto.setImageId(productImage.getImageId());
        return dto;
    }

    @Override
    public List<ProductImageDetailsDto> mapToImageDetailsDtoList(List<ProductImage> productImages) {
        if ( productImages == null )  return null;

        List<ProductImageDetailsDto> list = new ArrayList<>( productImages.size() );
        for ( ProductImage productImage : productImages ) {
            list.add( mapToImageDetailsDto(productImage) );
        }
        return list;
    }
}
