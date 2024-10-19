package com.ecom.security.common.config.kafka;

import com.ecom.security.common.config.dto.EventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class ConsumerDeserializer<T> implements Deserializer<EventDto<T>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> typeClass;

    public ConsumerDeserializer(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public EventDto<T> deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, objectMapper.getTypeFactory().constructParametricType(EventDto.class, typeClass));
        } catch (IOException e) {
            throw new SerializationException("Error deserializing UserRegisteredEvent", e);
        }
    }
}

