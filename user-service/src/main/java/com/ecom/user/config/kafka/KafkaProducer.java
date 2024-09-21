package com.ecom.user.config.kafka;

import com.ecom.user.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, EventDto> kafkaTemplate;
    private static final String TOPIC = "user-registered";

    public void sendUserRegisteredEvent(EventDto event) {
        kafkaTemplate.send(TOPIC, event);
    }
}