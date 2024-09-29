package com.ecom.inventory.business.service.listener;

import com.ecom.inventory.common.config.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryKafkaListener {

    @KafkaListener(topics = "product-created", groupId = "inventory-service-group", containerFactory = "listenerContainerFactory")
    public void handleUserRegisteredEvent(EventDto<?> event) {
        log.info("Received product created: {}", event);
    }
}
