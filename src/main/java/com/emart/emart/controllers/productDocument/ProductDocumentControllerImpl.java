package com.emart.emart.controllers.productDocument;

import com.emart.emart.dtos.ProductDocumentDetailsDto;
import com.emart.emart.dtos.ProductDocumentDto;
import com.emart.emart.services.product.ProductService;
import com.emart.emart.services.productDocument.ProductDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emart.emart.utility.Utility.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/documents")
public class ProductDocumentControllerImpl implements  ProductDocumentController{
    private final Logger logger = LoggerFactory.getLogger(ProductDocumentControllerImpl.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDocumentService productDocumentService;

    @Override
    @PostMapping("/")
    public ResponseEntity<Object> saveDocument(ProductDocumentDto productDocumentDto) {
        try
        {
            if (productDocumentService.saveProductDocument(productDocumentDto) == 0){
                logger.info("document saved successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseMsgDto("200 OK", "document saved successfully"));
            }
            else {
                logger.error("Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 NOT FOUND", "Product not found"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error saving the document",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error saving the document"));
        }
    }

    @Override
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteDocument(Long documentId) {
        try
        {
            if (productDocumentService.deleteDocument(documentId) == 0){
                logger.info("document deleted successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseMsgDto("200 OK", "Document deleted successfully"));
            }
            else {
                logger.info("document not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 NOT FOUND", "Document not found"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error deleting the document",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error deleting the document"));
        }
    }

    @Override
    @GetMapping("/all-documents")
    public ResponseEntity<Object> viewAllDocumentsByProductId(Long productId) {
        try
        {
            List<ProductDocumentDetailsDto> list = productDocumentService.viewAllDocuments(productId);
            if (!list.isEmpty()){
                logger.info("Document details list fetched successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseListDto("200 OK", "Document details list fetched successfully", list));
            }
            else {
                logger.info("No documents found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 NOT FOUND", "No documents found"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error fetching document details",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error fetching document details"));
        }
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Object> viewDocument(Long documentId) {
        try
        {
            ProductDocumentDto productDocumentDto = productDocumentService.viewDocument(documentId);
            if (productDocumentDto != null){
                logger.info("Document fetched successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Document fetched successfully", productDocumentDto));
            }
            else {
                logger.info("No documents found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 NOT FOUND", "No documents found"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error fetching document",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "Error fetching document"));
        }
    }
}
