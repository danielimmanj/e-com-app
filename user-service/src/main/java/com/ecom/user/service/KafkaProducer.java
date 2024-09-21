package com.ecom.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;
    private static final String TOPIC = "user-registered";

    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}