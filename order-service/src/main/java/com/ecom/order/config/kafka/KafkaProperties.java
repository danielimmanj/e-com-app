package com.ecom.order.config.kafka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaProperties {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;
}
