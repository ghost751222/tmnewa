package com.example.tmnewa.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JacksonUtils {

    @Getter
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static JsonNode toJsonNode(String jsonData) throws IOException {
        return mapper.readTree(jsonData);
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> classType) {

        return mapper.convertValue(map, classType);
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) throws JsonProcessingException, JsonMappingException {

        return mapper.readValue(content, valueTypeRef);
    }

    public static <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException, JsonMappingException {
        return mapper.readValue(content, valueType);
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    public static <T> T convertValue(Object fromValue, JavaType toValueType) {
        return mapper.convertValue(fromValue,toValueType);
    }


}
