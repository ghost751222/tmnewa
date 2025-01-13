package com.example.tmnewa.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class JacksonUtils {

    private static final ObjectMapper mapper = new ObjectMapper(){
        @Override
        public ObjectMapper registerModule(Module module) {
            return super.registerModule(new JavaTimeModule());
        }

        @Override
        public ObjectMapper disable(JsonParser.Feature... features) {
            return super.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
    };

    public static <T> T mapToObject(Map<String, Object> map, Class<T> classType) {

        return mapper.convertValue(map, classType);
    }


    public static <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException, JsonMappingException {
        return mapper.readValue(content,valueType);
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}
