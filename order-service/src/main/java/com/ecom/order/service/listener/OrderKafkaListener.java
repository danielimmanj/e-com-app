package com.ecom.order.service.listener;

import com.ecom.order.dto.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderKafkaListener {

    @KafkaListener(topics = "user-registered", groupId = "order-service-group", containerFactory = "listenerContainerFactory")
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("Received User Registered Event for user: {}", event);
    }
}
