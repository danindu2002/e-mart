package com.emart.emart.utility;

import com.emart.emart.dtos.ResponseItemDto;
import com.emart.emart.dtos.ResponseListDto;
import com.emart.emart.dtos.ResponseMsgDto;
import com.emart.emart.services.product.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class Utility {
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    // Methods for defining response objects
    public static ResponseMsgDto convertToResponseMsgDto(String status, String description) {
        ResponseMsgDto responseMsgDTO = new ResponseMsgDto();
        responseMsgDTO.setStatus(status);
        responseMsgDTO.setDescription(description);
        return responseMsgDTO;
    }

    public static ResponseListDto convertToResponseListDto(String status, String description, List<?> responseList) {
        ResponseListDto responseDTO = new ResponseListDto();
        responseDTO.setStatus(status);
        responseDTO.setDescription(description);
        responseDTO.setResponseList(responseList);
        return responseDTO;
    }

    public static ResponseItemDto convertToResponseItemDto(String status, String description, Object object) {
        ResponseItemDto responseItemDTO = new ResponseItemDto();
        responseItemDTO.setStatus(status);
        responseItemDTO.setDescription(description);
        responseItemDTO.setObject(object);
        return responseItemDTO;
    }

    // methods for file handling
    public static void saveBase64DocumentToFile(String base64Document, String filePath) {
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

    public static String convertDocumentToBase64(String filePath) {
        byte[] documentBytes;
        try {
            documentBytes = Files.readAllBytes(Path.of(filePath));
        } catch (IOException e) {
            logger.error("Error while converting document to base64", e);
            throw new RuntimeException("Error while converting document to base64", e);
        }
        return Base64.getEncoder().encodeToString(documentBytes);
    }
}
