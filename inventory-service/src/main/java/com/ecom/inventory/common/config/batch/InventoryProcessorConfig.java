package com.ecom.inventory.common.config.batch;

import com.ecom.inventory.business.dto.InventoryDto;
import com.ecom.inventory.business.model.Inventory;
import java.util.Objects;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class InventoryProcessorConfig {

    @Bean
    public ItemProcessor<InventoryDto, Inventory> inventoryProcessor() {
        return dto -> {
            if (Objects.isNull(dto.getProductName())
                    || Objects.isNull(dto.getProductId())
                    || Objects.isNull(dto.getProductPrice())
                    || dto.getProductPrice().compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Invalid product data: " + dto);
            Inventory stock = new Inventory();
            stock.setProductId(dto.getProductId());
            stock.setProductName(dto.getProductName());
            stock.setProductPrice(dto.getProductPrice());
            stock.setQuantity(dto.getQuantity());
            stock.setWarehouseLocation(dto.getWarehouseLocation());
            return stock;
        };
    }
}
