package com.ecom.product.common.config.kafka;

import com.ecom.product.common.config.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    @Autowired
    private final KafkaTemplate<String, EventDto<?>> kafkaTemplate;

    // Topics
    public static final String PRODUCT_CREATED_TOPIC = "product-created";
    public static final String PRODUCT_UPDATED_TOPIC = "product-updated";
    public static final String PRODUCT_DELETED_TOPIC = "product-deleted";
    public static final String CATEGORY_CREATED_TOPIC = "category-created";
    public static final String CATEGORY_UPDATED_TOPIC = "category-updated";
    public static final String CATEGORY_DELETED_TOPIC = "category-deleted";
    public static final String INVENTORY_UPDATED_TOPIC = "inventory-updated";

    // Generic method to send events to any topic
    public void sendEvent(String topic, EventDto<?> event) {
        kafkaTemplate.send(topic, event);
    }
}
