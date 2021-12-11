package com.horvee.storylog.springboot.configuration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class StoryLogJsonUtil {

    private static final ObjectMapper objectMapperToDefault = new ObjectMapper(){{
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);// Out put is null field
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.setTimeZone(TimeZone.getDefault());
    }};

    private static final ObjectMapper objectMapperToSimpleDate = new ObjectMapper() {{
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.setTimeZone(TimeZone.getDefault());
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }};

    /**
     * Cover to json
     * Date out put format: yyyy-MM-dd HH:mm:ss
     * */
    public static String toJson(Object obj) throws RuntimeException {
        try {
            return objectMapperToSimpleDate.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("To json fail",e);
        }
    }

    /**
     * Cover to object
     * Use date format 'yyyy-MM-dd HH:mm:ss'
     * */
    public static <T> T formJson(String content,Class<T> targetClass) throws RuntimeException {
        try {
            return objectMapperToSimpleDate.readValue(content,targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("To object fail",e);
        }
    }

    /**
     * Sample: List<String> r = formJson(jsonContent, new TypeReference<List<String>>() {});
     * Use date format 'yyyy-MM-dd HH:mm:ss'
     * */
    public static <T> T formJson(String content, TypeReference<T> valueTypeRef) throws RuntimeException {
        try {
            return objectMapperToSimpleDate.readValue(content,valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("To object fail",e);
        }
    }


    /**
     * Use default to json,date object type by long
     * */
    public static String defaultToJson(Object obj) throws RuntimeException {
        try {
            return objectMapperToDefault.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("To json fail",e);
        }
    }

    /**
     * Use default to json,date object type by long
     * */
    public static <T> T defaultFormJson(String content,Class<T> targetClass) throws RuntimeException {
        try {
            return objectMapperToDefault.readValue(content,targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("To object fail",e);
        }
    }

    /**
     * Use default to json,date object type by long
     * Sample: List<String> r = formJson(jsonContent, new TypeReference<List<String>>() {});
     *
     * */
    public static <T> T defaultFormJson(String content,TypeReference<T> valueTypeRef) throws RuntimeException {
        try {
            return objectMapperToDefault.readValue(content,valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("To object fail",e);
        }
    }

}
