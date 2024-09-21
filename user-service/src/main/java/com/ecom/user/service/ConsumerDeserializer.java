package com.ecom.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class ConsumerDeserializer implements Deserializer<UserRegisteredEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserRegisteredEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, UserRegisteredEvent.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing UserRegisteredEvent", e);
        }
    }
}