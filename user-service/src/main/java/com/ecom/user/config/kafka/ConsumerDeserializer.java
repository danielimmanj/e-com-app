package com.ecom.user.config.kafka;

import com.ecom.user.dto.EventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class ConsumerDeserializer implements Deserializer<EventDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EventDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, EventDto.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing UserRegisteredEvent", e);
        }
    }
}

