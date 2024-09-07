package com.yicj.study.jpa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * @author yicj
 * @since 2024/9/7 7:55
 */
@Slf4j
public class JsonConverter {
    private static final ObjectMapper mapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

    private static final ObjectMapper viewMapper =
            new ObjectMapper().disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

    public static String serializeObject(Object object) {
        if(Objects.isNull(object)) {
            return null;
        }

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[JsonConverter] serialize error:", e);
        }
        return null;
    }

    public static byte[] serializeObjectAsBytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("[JsonConverter] serialize error:", e);
        }
        return null;
    }

    public static String serializeObject(Object object, Class<?> serializationView) {
        try {
            return viewMapper.writerWithView(serializationView).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[JsonConverter] serialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(String json, Class<T> classOfT) {
        try {
            if (Strings.isNullOrWhitespace(json) || classOfT == null) {
                log.warn("[JsonConverter] deserialize json cannot be null!");
                return null;
            }
            return mapper.readValue(json, classOfT);
        } catch (Exception e) {
            if (String.class.equals(classOfT)) {
                return classOfT.cast(json);
            }
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(String json, JavaType type) {
        try {
            if (Strings.isNullOrWhitespace(json) || type == null) {
                log.warn("[JsonConverter] deserialize json cannot be null!");
                return null;
            }
            return mapper.readValue(json, type);
        } catch (Exception e) {
            log.error("json deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(byte[] json, JavaType type) {
        try {
            if (json == null || json.length == 0 || type == null) {
                log.warn("[JsonConverter] deserialize json byte cannot be null!");
                return null;
            }
            return mapper.readValue(json, type);
        } catch (Exception e) {
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(String json, Class<?> parametrized,
                                          Class<?>... parameterClasses) {
        if (Strings.isNullOrWhitespace(json) || parametrized == null || parameterClasses == null) {
            log.warn("[JsonConverter] deserialize json cannot be null!");
            return null;
        }
        JavaType type = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(byte[] json, Class<?> parametrized,
                                          Class<?>... parameterClasses) {
        if (json == null || json.length == 0 || parametrized == null || parameterClasses == null) {
            log.warn("[JsonConverter] deserialize json byte cannot be null!");
            return null;
        }
        JavaType type = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public static <T> T deserializeObject(String json, TypeReference valueTypeRef) {
        if (Strings.isNullOrWhitespace(json)) {
            log.warn("[JsonConverter] deserialize json cannot be null!");
            return null;
        }
        try {
            return (T)mapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(String json, Class<? extends Collection<?>> collectionClass,
                                          Class<?> elementClass) {
        if (Strings.isNullOrWhitespace(json)) {
            log.warn("[JsonConverter] deserialize json cannot be null!");
            return null;
        }
        try {
            val type = mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
            return mapper.readValue(json, type);
        } catch (Exception e) {
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(byte[] json, Class<T> classOfT) {
        if (json == null || json.length == 0 || classOfT == null) {
            log.warn("[JsonConverter] deserialize json byte cannot be null!");
            return null;
        }
        try {
            return mapper.readValue(json, classOfT);
        } catch (Exception e) {
            if (String.class.equals(classOfT)) {
                return classOfT.cast(json);
            }
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static <T> T deserializeObject(JsonNode json, Class<T> classOfT) {
        try {
            if (String.class.equals(classOfT))
                return classOfT.cast(json.toString());
            return mapper.treeToValue(json, classOfT);
        } catch (Exception e) {
            if (String.class.equals(classOfT)) {
                return classOfT.cast(json);
            }
            log.error("[JsonConverter] deserialize error:", e);
        }
        return null;
    }

    public static JavaType getJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
        if (parameterClasses == null || parameterClasses.length == 0) {
            return mapper.getTypeFactory().constructType(parametrized);
        }
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public static String prettyPrint(String json) {
        if (Strings.isNullOrWhitespace(json))
            return Strings.EMPTY;
        try {
            val node = mapper.readValue(json, JsonNode.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            log.error("[JsonConverter] prettyPrint error:", e);
            return null;
        }
    }

    public static String prettyPrint(Object object) {
        if (object == null)
            return Strings.EMPTY;
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[JsonConverter] prettyPrint error:", e);
            return null;
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

}
