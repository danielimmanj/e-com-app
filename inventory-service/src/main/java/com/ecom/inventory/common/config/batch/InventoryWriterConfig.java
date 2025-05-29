package com.ecom.inventory.common.config.batch;

import com.ecom.inventory.business.model.Inventory;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryWriterConfig {

    @Bean
    public JpaItemWriter<Inventory> inventoryWriter(EntityManagerFactory emf) {
        JpaItemWriter<Inventory> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }
}

