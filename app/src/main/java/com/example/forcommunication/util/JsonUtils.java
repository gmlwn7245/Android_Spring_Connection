package com.example.forcommunication.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> Optional<T> readValue(String content, Class<T> clazz) {
        try {
            T result = objectMapper.readValue(content, clazz);
            return Optional.of(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static <T> Optional<T> readValue(String content, TypeReference<T> typeReference) {
        try {
            T result = objectMapper.readValue(content, typeReference);
            return Optional.of(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<String> writeValue(Object value) {
        try {
            String result = objectMapper.writeValueAsString(value);
            return Optional.of(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
