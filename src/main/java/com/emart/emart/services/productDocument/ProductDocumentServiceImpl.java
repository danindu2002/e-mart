package com.emart.emart.services.productDocument;

import com.emart.emart.dtos.productDocumentDtos.ProductDocumentDetailsDto;
import com.emart.emart.dtos.productDocumentDtos.ProductDocumentDto;
import com.emart.emart.models.Product;
import com.emart.emart.models.ProductDocument;
import com.emart.emart.repositories.ProductDocumentRepo;
import com.emart.emart.repositories.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

import static com.emart.emart.utility.Utility.convertDocumentToBase64;
import static com.emart.emart.utility.Utility.saveBase64DocumentToFile;

@Service
public class ProductDocumentServiceImpl implements ProductDocumentService{
    private final Logger logger = LoggerFactory.getLogger(ProductDocumentServiceImpl.class);

    @Value("${baseUrl.fileLocation}")
    private String baseFileLocation;

    @Autowired
    ProductDocumentRepo productDocumentRepo;
    @Autowired
    ProductRepo productRepo;

    // saving a product document
    @Override
    public int saveProductDocument(ProductDocumentDto productDocumentDto) {
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productDocumentDto.getProductId());

        if (product != null) {
            // Validating file type
            if (!isValidPdf(productDocumentDto.getDocument())) {
                logger.error("Invalid document type. Only PDF files are allowed");
                return 1;
            }

            // Saving the document
            String documentFileName = productDocumentDto.getDocumentName() + "_" + System.currentTimeMillis() + ".pdf";

            // Change the base file location from yml file
            String documentFilePath = baseFileLocation + "\\documents\\" + product.getProductCode() + "\\" + documentFileName;
            saveBase64DocumentToFile(productDocumentDto.getDocument(), documentFilePath);

            ProductDocument productDocument = new ProductDocument();
            productDocument.setDocumentName(documentFileName);
            productDocument.setDocumentDescription(productDocumentDto.getDocumentDescription());
            productDocument.setDocumentPath(documentFilePath);
            productDocument.setProduct(product);
            productDocumentRepo.save(productDocument);

            logger.info("document saved");
            return 0;
        }
        else {
            logger.error("product not found");
            return 2;
        }
    }

    // deleting a product document
    @Override
    public int deleteDocument(Long documentId) {
        ProductDocument productDocument = productDocumentRepo.findByDocumentIdAndDeletedIsFalse(documentId);
        if (productDocument != null) {
            productDocument.setDeleted(true);
            productDocumentRepo.save(productDocument);

            logger.info("document marked as deleted");
            return 0;
        }
        else {
            logger.info("document not found");
            return 1;
        }
    }

    // fetching all document details without the file itself
    @Override
    public List<ProductDocumentDetailsDto> viewAllDocuments(Long productId) {
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productId);
        logger.info("fetched document details list");
        return mapToDocumentDetailsDtoList(productDocumentRepo.findAllByProductAndDeletedIsFalse(product));
    }

    // fetching the document
    @Override
    public ProductDocumentDto viewDocument(Long documentId) {
        ProductDocument productDocument = productDocumentRepo.findByDocumentIdAndDeletedIsFalse(documentId);
        if (productDocument != null) {
            logger.info("document fetched");
            return mapTopDocumentDto(productDocument);
        }
        else return null;
    }

    // helper methods for mapping custom DTOs
    @Override
    public ProductDocumentDto mapTopDocumentDto(ProductDocument productDocument)
    {
        ProductDocumentDto dto = new ProductDocumentDto();
        dto.setDocumentDescription(productDocument.getDocumentDescription());
        dto.setDocumentName(productDocument.getDocumentName());
        dto.setProductId(productDocument.getProduct().getProductId());
        dto.setDocument(convertDocumentToBase64(productDocument.getDocumentPath()));
        return dto;
    }

    @Override
    public ProductDocumentDetailsDto mapToDocumentDetailsDto(ProductDocument productDocument)
    {
        ProductDocumentDetailsDto dto = new ProductDocumentDetailsDto();
        dto.setDocumentId(productDocument.getDocumentId());
        dto.setDocumentName(productDocument.getDocumentName());
        dto.setDocumentDescription(productDocument.getDocumentDescription());
        dto.setProductName(productDocument.getProduct().getProductName());
        dto.setProductCode(productDocument.getProduct().getProductCode());
        return dto;
    }

    @Override
    public List<ProductDocumentDetailsDto> mapToDocumentDetailsDtoList(List<ProductDocument> productDocuments) {
        if ( productDocuments == null )  return null;

        List<ProductDocumentDetailsDto> list = new ArrayList<>( productDocuments.size() );
        for ( ProductDocument productDocument : productDocuments ) {
            list.add( mapToDocumentDetailsDto( productDocument ) );
        }
        return list;
    }

    // Method to validate the file type as PDF
    private boolean isValidPdf(String base64String) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);
            File tempFile = File.createTempFile("tempFile", ".pdf");

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(decodedBytes);
            }
            String fileContents = new String(Files.readAllBytes(tempFile.toPath()));
            return fileContents.startsWith("%PDF");
        } catch (Exception e) {
            logger.error("Error validating PDF file", e);
            return false;
        }
    }
}
