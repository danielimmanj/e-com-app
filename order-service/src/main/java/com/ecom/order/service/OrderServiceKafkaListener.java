package com.ecom.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceKafkaListener {

    @KafkaListener(topics = "user-registered", groupId = "order-service-group", containerFactory = "listenerContainerFactory")
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("Received User Registered Event for user: {}", event);
        log.info("Received User Registered Event for user ID: {}", event.getUserId());
        log.info("User snapshot saved in Order Service database for user ID: {}", event.getEmail());
    }
}
