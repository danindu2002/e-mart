package com.emart.emart.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public class AESConverter implements AttributeConverter<String, String> {

    @Autowired
    private AESHandler aesHandler;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return aesHandler.encrypt(attribute);
        } catch (Exception e) {
            // Handle encryption exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aesHandler.decrypt(dbData);
        } catch (Exception e) {
            // Handle decryption exception
            e.printStackTrace();
            return null;
        }
    }
}