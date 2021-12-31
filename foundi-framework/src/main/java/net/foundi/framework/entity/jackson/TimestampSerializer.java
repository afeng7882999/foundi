/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.jackson;

import cn.hutool.core.date.TemporalAccessorUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Map;

/**
 * Jackson序列化器，TemporalAccessor序列化为Long
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class TimestampSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private JavaType contentType;

    /**
     * 将TemporalAccessor序列化的值写入JsonGenerator
     */
    private static void writeTimestamp(JsonGenerator gen, TemporalAccessor value) throws IOException {
        // 转换为时间戳，使用系统默认时区，系统当前日期
        long epoch = TemporalAccessorUtil.toEpochMilli((TemporalAccessor) value);
        gen.writeString(Long.toString(epoch));
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            provider.defaultSerializeNull(gen);
            return;
        }

        if (value instanceof TemporalAccessor) {
            writeTimestamp(gen, (TemporalAccessor) value);
        } else {
            throw new JsonMappingException(gen, "TimestampSerializer不支持类型'" + contentType.getTypeName() + "'的序列化");
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            throw new JsonMappingException(prov.getGenerator(), "TimestampSerializer不能单独使用，请在声明中使用，比如@JsonTimestamp");
        }

        contentType = property.getType();
        if (contentType.isCollectionLikeType()) {
            return new TimestampCollectionSerializer();
        }
        if (contentType.isMapLikeType()) {
            return new TimestampMapSerializer();
        }
        if (contentType.isArrayType()) {
            return new TimestampArraySerializer();
        }
        return this;
    }

    /**
     * TemporalAccessor集合序列化器
     */
    static class TimestampCollectionSerializer extends JsonSerializer<Collection<TemporalAccessor>> {

        @Override
        public void serialize(Collection<TemporalAccessor> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            int len = value.size();
            gen.writeStartArray(value, len);
            serializeContents(value, gen, provider);
            gen.writeEndArray();

        }

        private void serializeContents(Collection<TemporalAccessor> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            int i = 0;
            try {
                for (TemporalAccessor ta : value) {
                    if (ta == null) {
                        provider.defaultSerializeNull(gen);
                        continue;
                    }
                    writeTimestamp(gen, ta);
                    ++i;
                }
            } catch (Exception e) {
                throw new JsonMappingException(gen, "TimestampSerializer未能序列化'" + value + "'中的第" + i + "个");
            }
        }
    }

    /**
     * TemporalAccessor数组序列化器
     */
    static class TimestampArraySerializer extends JsonSerializer<TemporalAccessor[]> {

        @Override
        public void serialize(TemporalAccessor[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            int len = value.length;
            gen.writeStartArray(value, len);
            serializeContents(value, gen, provider);
            gen.writeEndArray();

        }

        private void serializeContents(TemporalAccessor[] value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            int i = 0;
            try {
                for (TemporalAccessor ta : value) {
                    if (ta == null) {
                        provider.defaultSerializeNull(gen);
                        continue;
                    }
                    writeTimestamp(gen, ta);
                    ++i;
                }
            } catch (Exception e) {
                throw new JsonMappingException(gen, "TimestampSerializer未能序列化'" +  value[i] + "'");
            }
        }
    }

    /**
     * TemporalAccessor Map序列化器
     */
    static class TimestampMapSerializer extends JsonSerializer<Map<String, TemporalAccessor>> {

        @Override
        public void serialize(Map<String, TemporalAccessor> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject(value);
            serializeFields(value, gen, provider);
            gen.writeEndObject();

        }

        public void serializeFields(Map<String, TemporalAccessor> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            String keyElem = null;
            TemporalAccessor valueElem = null;
            try {
                for (Map.Entry<String, TemporalAccessor> entry : value.entrySet()) {
                    keyElem = entry.getKey();
                    valueElem = entry.getValue();
                    if (valueElem == null) {
                        provider.defaultSerializeNull(gen);
                        continue;
                    }
                    gen.writeFieldName(keyElem);
                    writeTimestamp(gen, valueElem);
                }
            } catch (Exception e) {
                throw new JsonMappingException(gen, "TimestampSerializer未能序列化'" + keyElem + ":" + valueElem + "'");
            }
        }
    }

}
