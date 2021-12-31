/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.jackson;

import cn.hutool.core.date.TemporalAccessorUtil;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.apache.commons.collections4.map.HashedMap;

import java.io.IOException;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Jackson反序列化器，Long反序列化为LocalDatetime、LocalDate、LocalTime
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class TimestampDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private JavaType contentType;

    /**
     * 解析timestamp字符串
     *
     * @param timestamp timestamp字符串
     * @param contentType  解析类型
     * @return TemporalAccessor对象
     */
    private static TemporalAccessor parseTimestamp(String timestamp, JavaType contentType) {
        if (StringUtils.isBlank(timestamp)) {
            return null;
        }
        long ts = Long.parseLong(timestamp);
        if (contentType.hasRawClass(LocalDateTime.class)) {
            return DateUtils.of(ts);
        }
        if (contentType.hasRawClass(LocalDate.class)) {
            return DateUtils.of(ts).toLocalDate();
        }
        if (contentType.hasRawClass(LocalTime.class)) {
            return DateUtils.of(ts).toLocalTime();
        }

        throw new IllegalArgumentException("不支持类型'" + contentType.getTypeName() + "'的反序列化");
    }

    @Override
    public TemporalAccessor deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        try {
            return parseTimestamp(jsonparser.getText(), contentType);
        } catch (IllegalArgumentException e) {
            throw new JsonMappingException(jsonparser, e.getMessage());
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            throw new JsonMappingException(ctxt.getParser(), "TimestampDeserializer不能单独使用，请在声明中使用，比如@JsonTimestamp");
        }

        JavaType javaType = property.getType();
        if (javaType.isCollectionLikeType()) {
            return new TimestampCollectionDeserializer(javaType, javaType.getContentType());
        }
        if (javaType.isMapLikeType()) {
            return new TimestampMapDeserializer(javaType.getContentType());
        }
        if (javaType.isArrayType()) {
            return new TimestampArrayDeserializer(javaType.getContentType());
        }

        contentType = javaType;
        return this;
    }

    /**
     * TemporalAccessor集合反序列化器
     */
    static class TimestampCollectionDeserializer extends JsonDeserializer<Collection<TemporalAccessor>> {

        private final JavaType collectionType;
        private final JavaType contentType;

        public TimestampCollectionDeserializer(JavaType collectionType, JavaType contentType) {
            this.collectionType = collectionType;
            this.contentType = contentType;
        }

        @Override
        public Collection<TemporalAccessor> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            if (collectionType.hasRawClass(List.class)) {
                Collection<TemporalAccessor> result = new ArrayList<>();
                deserializeAndAdd(result, p);
                return result;
            } else if (collectionType.hasRawClass(Set.class)) {
                Collection<TemporalAccessor> result = new HashSet<>();
                deserializeAndAdd(result, p);
                return result;
            }
            throw new JsonMappingException(ctxt.getParser(), "TimestampCollectionDeserializer不支持类型'" + collectionType.getTypeName() + "'的反序列化");
        }

        private void deserializeAndAdd(Collection<TemporalAccessor> collection, JsonParser p) throws JsonMappingException {
            String value = null;
            try {
                while (true) {
                    value = p.nextTextValue();
                    if (value == null) {
                        JsonToken t = p.currentToken();
                        if (t == JsonToken.END_ARRAY) {
                            break;
                        }
                        if (t == JsonToken.VALUE_NULL) {
                            collection.add(null);
                        }
                    } else {
                        collection.add(parseTimestamp(value, contentType));
                    }
                }
            } catch (Exception e) {
                throw new JsonMappingException(p, "TimestampCollectionDeserializer解析'" + value + "'出错: " + e.getMessage());
            }
        }
    }

    /**
     * TemporalAccessor数组反序列化器
     */
    static class TimestampArrayDeserializer extends JsonDeserializer<TemporalAccessor[]> {

        private final JavaType contentType;

        public TimestampArrayDeserializer(JavaType contentType) {
            this.contentType = contentType;
        }

        @Override
        public TemporalAccessor[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            final ObjectBuffer buffer = ctxt.leaseObjectBuffer();
            Object[] chunk = buffer.resetAndStart();
            int ix = 0;
            try {
                while (true) {
                    String value = p.nextTextValue();
                    if (value == null) {
                        JsonToken t = p.currentToken();
                        if (t == JsonToken.END_ARRAY) {
                            break;
                        }
                    }
                    if (ix >= chunk.length) {
                        chunk = buffer.appendCompletedChunk(chunk);
                        ix = 0;
                    }
                    chunk[ix++] = value != null ? parseTimestamp(value, contentType) : null;
                }
            } catch (Exception e) {
                throw JsonMappingException.wrapWithPath(e, chunk, buffer.bufferedSize() + ix);
            }

            TemporalAccessor[] result;

            if (contentType.hasRawClass(LocalDateTime.class)) {
                result = buffer.completeAndClearBuffer(chunk, ix, LocalDateTime.class);
            } else if (contentType.hasRawClass(LocalDate.class)) {
                result = buffer.completeAndClearBuffer(chunk, ix, LocalDate.class);
            } else {
                result = buffer.completeAndClearBuffer(chunk, ix, LocalTime.class);
            }

            ctxt.returnObjectBuffer(buffer);
            return result;
        }
    }

    /**
     * TemporalAccessor Map反序列化器, key为String
     */
    static class TimestampMapDeserializer extends JsonDeserializer<Map<String, TemporalAccessor>> {

        private final JavaType contentType;

        public TimestampMapDeserializer(JavaType contentType) {
            this.contentType = contentType;
        }

        @Override
        public Map<String, TemporalAccessor> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            if (p.currentTokenId() != JsonTokenId.ID_START_OBJECT ) {
                throw new JsonMappingException(ctxt.getParser(), "TimestampMapDeserializer不支持非Map类型的反序列化");
            }
            Map<String, TemporalAccessor> result = new HashedMap<>();
            readAndBindStringKeyMap(p, ctxt, result);
            return result;
        }

        protected final void readAndBindStringKeyMap(JsonParser p, DeserializationContext ctxt, Map<String, TemporalAccessor> result) throws IOException {
            String key = null;
            try {
                if (p.isExpectedStartObjectToken()) {
                    key = p.nextFieldName();
                } else {
                    JsonToken t = p.currentToken();
                    if (t == JsonToken.END_OBJECT) {
                        return;
                    }
                    if (t != JsonToken.FIELD_NAME) {
                        ctxt.reportWrongTokenException(this, JsonToken.FIELD_NAME, null);
                    }
                    key = p.currentName();
                }

                for (; key != null; key = p.nextFieldName()) {
                    JsonToken t = p.nextToken();
                    if (t == JsonToken.VALUE_NULL) {
                        result.put(key, null);
                    } else {
                        String value = p.getText();
                        result.put(key, parseTimestamp(value, contentType));
                    }
                }

            } catch (Exception e) {
                throw new JsonMappingException(p, "TimestampMapDeserializer解析key为'" + key + "'的字段出错: " + e.getMessage());
            }
        }
    }

}
