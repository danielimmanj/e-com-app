package com.ecom.security.common.config.kafka;

import com.ecom.security.common.config.dto.EventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

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

