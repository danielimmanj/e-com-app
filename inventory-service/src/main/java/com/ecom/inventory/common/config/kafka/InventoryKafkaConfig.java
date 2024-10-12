package com.ecom.inventory.common.config.kafka;

import com.ecom.inventory.business.dto.InventoryDto;
import com.ecom.inventory.common.config.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class InventoryKafkaConfig {

    private final CustomKafkaProperties customKafkaProperties;

    @Bean
    public KafkaTemplate<String, EventDto<?>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, EventDto<?>> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, customKafkaProperties.getBootstrapServer());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ConsumerFactory<String, EventDto<?>> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, customKafkaProperties.getBootstrapServer());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, customKafkaProperties.getConsumerGroupId());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, customKafkaProperties.getTrustedPackages());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ConsumerDeserializer.class);
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, Boolean.FALSE);
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(), new ConsumerDeserializer(InventoryDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventDto<?>> listenerContainerFactory(ConsumerFactory<String, EventDto<?>> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, EventDto<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public NewTopic productUpdatedTopic() {
        return TopicBuilder.name("inventory-updated")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
