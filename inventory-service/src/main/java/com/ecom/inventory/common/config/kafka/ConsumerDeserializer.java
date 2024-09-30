package com.ecom.inventory.common.config.kafka;

import com.ecom.inventory.business.model.Inventory;
import com.ecom.inventory.common.config.dto.EventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@NoArgsConstructor
public class ConsumerDeserializer implements Deserializer<EventDto<?>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<?> typeClass;

    public ConsumerDeserializer(Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public EventDto<?> deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, objectMapper.getTypeFactory().constructParametricType(EventDto.class, typeClass));
        } catch (IOException e) {
            throw new SerializationException("Error deserializing UserRegisteredEvent", e);
        }
    }
}

