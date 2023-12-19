package com.emart.emart.services.product;

import com.emart.emart.dtos.ProductDto;
import com.emart.emart.mappers.ProductMapper;
import com.emart.emart.models.Product;
import com.emart.emart.models.ProductDocument;
import com.emart.emart.repositories.ProductDocumentRepo;
import com.emart.emart.repositories.ProductRepo;
import com.emart.emart.repositories.reference.RefCategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepo productRepo;
    @Autowired
    RefCategoryRepo refCategoryRepo;
    @Autowired
    ProductDocumentRepo productDocumentRepo;

    @Override
    public void saveProduct(Product product) {

//        // product images uploading
//        if (!product.getProductImagesPath().isEmpty()) {
//            // Saving an image
//            String imageName = "image_" + System.currentTimeMillis() + ".jpg";
//
//            // Change the base file location from yml file
//            String imagePath = baseFileLocation + "\\images\\" + product.getProductCode() + "\\" + imageName;
//            saveBase64DocumentToFile(product.getProductImagesPath(), imagePath);
//            product.setProductImagesPath(String.valueOf(Paths.get(imagePath).getParent()));
//        }

        product.setCategory(refCategoryRepo.findByRefCategoryId(Long.valueOf(product.getCategory())).getRefCategoryName());
        productRepo.save(product);
    }

    @Override
    public ProductDto viewProduct(Long productId) {
        logger.info("fetched product");
        return ProductMapper.productMapper.mapToProductDto(productRepo.findByProductIdAndDeletedIsFalse(productId));
    }

    @Override
    public List<ProductDto> viewAllProducts() {
        logger.info("all products fetched");
        return ProductMapper.productMapper.maptoProductDtoList(productRepo.findAllByDeletedIsFalse());
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        logger.info("products searched globally");
        return ProductMapper.productMapper.maptoProductDtoList(productRepo.search(keyword));
    }

//    @Override
//    public List<ProductDto> searchProductsByFilters(String category, Double minPrice, Double maxPrice) {
//        String categoryName = refCategoryRepo.findByRefCategoryId(Long.valueOf(category)).getRefCategoryName();
//        logger.info("products searched by filters");
//        return ProductMapper.productMapper.maptoProductDtoList(productRepo.findByPriceAndCategory(minPrice, maxPrice, categoryName));
//    }

    @Override
    public List<ProductDto> searchByPrice(Double minPrice, Double maxPrice) {
        logger.info("products searched by price");
        return ProductMapper.productMapper.maptoProductDtoList(productRepo.searchByPrice(minPrice, maxPrice));
    }

    @Override
    public List<ProductDto> searchByCategory(String category) {
        String categoryName = refCategoryRepo.findByRefCategoryId(Long.valueOf(category)).getRefCategoryName();
        logger.info("products searched by category");
        return ProductMapper.productMapper.maptoProductDtoList(productRepo.searchByCategoryAndDeletedIsFalse(categoryName));
    }

    @Override
    public List<ProductDto> searchByPriceAndCategory(String category, Double minPrice, Double maxPrice) {
        String categoryName = refCategoryRepo.findByRefCategoryId(Long.valueOf(category)).getRefCategoryName();
        logger.info("products searched by price and category");
        return ProductMapper.productMapper.maptoProductDtoList(productRepo.searchByPriceAndCategory(minPrice, maxPrice, categoryName));
    }


    @Override
    public int updateProduct(Long productId, Product product) {
        Product updatedProduct = productRepo.findByProductIdAndDeletedIsFalse(productId);
        if (updatedProduct == null) {
            logger.error("product not found");
            return 2;
        } else {
            Product product1 = productRepo.findByProductCodeAndDeletedIsFalse(product.getProductCode());
            if (product1 == null || Objects.equals(product1.getProductId(), productId))
            {
                updatedProduct.setProductName(product.getProductName());
                updatedProduct.setDescription(product.getDescription());
                updatedProduct.setQuantity(product.getQuantity());
                updatedProduct.setRating(product.getRating());
                updatedProduct.setPrice(product.getPrice());
                updatedProduct.setSize(product.getSize());
                updatedProduct.setColor(product.getColor());
                updatedProduct.setCategory(refCategoryRepo.findByRefCategoryId(Long.valueOf(product.getCategory())).getRefCategoryName());
                productRepo.save(updatedProduct);

                logger.info("product updated");
                return 0;
            } else {
                logger.info("duplicate product code found");
                return 1;
            }
        }
    }

    @Override
    public int deleteProduct(Long productId) {
        Product deletedProduct = productRepo.findByProductIdAndDeletedIsFalse(productId);
        if (deletedProduct == null) {
            logger.error("product not found");
            return 1;
        }
        else {
            // Deleting existing documents if exists
            List<ProductDocument> productDocumentList =  productDocumentRepo.findAllByProductAndDeletedIsFalse(deletedProduct);
            if (productDocumentList != null){
                for (ProductDocument productDocument : productDocumentList) {
                    productDocument.setDeleted(true);
                    productDocumentRepo.save(productDocument);
                }
            }

            deletedProduct.setDeleted(true);
            productRepo.save(deletedProduct);
            logger.info("product deleted");
            return 0;
        }
    }

    // Helper method to delete document file
//    @Override
//    public void deleteDocumentFile(String filePath)
//    {
//        try {
//            // Delete the document file
//            Files.deleteIfExists(Path.of(filePath));
//
//            // Delete the parent folder
//            Files.deleteIfExists(Paths.get(filePath).getParent());
//        }
//        catch (IOException e) {
//            logger.error("Error while deleting document file", e);
//            throw new RuntimeException("Error while deleting document file", e);
//        }
//    }
}
