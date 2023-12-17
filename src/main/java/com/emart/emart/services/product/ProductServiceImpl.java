package com.emart.emart.services.product;

import com.emart.emart.dtos.ProductDto;
import com.emart.emart.mappers.ProductMapper;
import com.emart.emart.models.Product;
import com.emart.emart.repositories.ProductRepo;
import com.emart.emart.repositories.ref.RefCategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    public String baseFileLocation = "D:\\OneDrive - Informatics Holdings\\Evaluation Tasks\\e-mart";

    @Autowired
    ProductRepo productRepo;
    @Autowired
    RefCategoryRepo refCategoryRepo;

    @Override
    public void saveProduct(Product product) {

        if (!product.getDocumentPath().isEmpty()) {
            // Saving the document
            String documentFileName = "document_" + System.currentTimeMillis() + ".pdf";

            // Base file location defined at the top
            String documentFilePath = baseFileLocation + "\\documents\\" + product.getProductCode() + "\\" + documentFileName;
            saveBase64DocumentToFile(product.getDocumentPath(), documentFilePath);
            product.setDocumentPath(documentFilePath);
        }
        else product.setDocumentPath("");

        product.setCategory(refCategoryRepo.findByRefCategoryId(Long.valueOf(product.getCategory())).getRefCategoryName());
        productRepo.save(product);
    }

    @Override
    public ProductDto viewProduct(Long productId) {
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productId);

        // converting the file to base64 if exists
        if (!product.getDocumentPath().isEmpty()) {
            String base64Document = convertDocumentToBase64(product.getDocumentPath());
            product.setDocumentPath(base64Document);
        }
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
            String currentDocumentPath = updatedProduct.getDocumentPath();

            if (product1 == null || Objects.equals(product1.getProductId(), productId)) {
                updatedProduct.setProductName(product.getProductName());
//                updatedProduct.setProductCode(product.getProductCode());
                updatedProduct.setDescription(product.getDescription());
                updatedProduct.setQuantity(product.getQuantity());
                updatedProduct.setRating(product.getRating());
                updatedProduct.setPrice(product.getPrice());
                updatedProduct.setSize(product.getSize());
                updatedProduct.setColor(product.getColor());
                updatedProduct.setCategory(refCategoryRepo.findByRefCategoryId(Long.valueOf(product.getCategory())).getRefCategoryName());
                productRepo.save(updatedProduct);

                if (!currentDocumentPath.isEmpty()){
                    // Deleting the existing document file
                    deleteDocumentFile(currentDocumentPath);

                    // Saving the new base64 document to file
                    saveBase64DocumentToFile(product.getDocumentPath(), updatedProduct.getDocumentPath());
                }

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
            // Deleting the existing document file if exists
            if (!deletedProduct.getDocumentPath().isEmpty()) {
                deleteDocumentFile(deletedProduct.getDocumentPath());
            }
            deletedProduct.setDeleted(true);
            productRepo.save(deletedProduct);
            logger.info("product deleted");
            return 0;
        }
    }


    // Helper method to save base64 document to file
    @Override
    public void saveBase64DocumentToFile(String base64Document, String filePath) {
        try {
            // Creating a file if not exists
            Path directoryPath = Paths.get(filePath).getParent();
            Files.createDirectories(directoryPath);

            // Saving the base64 document to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                byte[] decodedBytes = Base64.getDecoder().decode(base64Document);
                fos.write(decodedBytes);
            }
        } catch (IOException e) {
            logger.error("Error while saving base64 document to file", e);
            throw new RuntimeException("Error while saving base64 document to file", e);
        }
    }

    // Helper method to convert document to base64
    @Override
    public String convertDocumentToBase64(String filePath) {
        byte[] documentBytes;
        try {
            documentBytes = Files.readAllBytes(Path.of(filePath));
        } catch (IOException e) {
            logger.error("Error while converting document to base64", e);
            throw new RuntimeException("Error while converting document to base64", e);
        }
        return Base64.getEncoder().encodeToString(documentBytes);
    }

    // Helper method to delete document file
    @Override
    public void deleteDocumentFile(String filePath)
    {
        try {
            // Delete the document file
            Files.deleteIfExists(Path.of(filePath));

            // Delete the parent folder
            Files.deleteIfExists(Paths.get(filePath).getParent());
        }
        catch (IOException e) {
            logger.error("Error while deleting document file", e);
            throw new RuntimeException("Error while deleting document file", e);
        }
    }
}
