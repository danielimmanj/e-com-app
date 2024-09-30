package com.ecom.inventory.business.service.listener;

import com.ecom.inventory.business.dto.InventoryDto;
import com.ecom.inventory.business.model.Inventory;
import com.ecom.inventory.business.service.InventoryService;
import com.ecom.inventory.common.config.dto.EventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryKafkaListener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "product-created",
            groupId = "inventory-service-group",
            containerFactory = "listenerContainerFactory"
//            ,properties = {"spring.json.value.default.type=com.ecom.inventory.common.config.dto.EventDto"}
    )
    public void handleUserRegisteredEvent(EventDto<?> event) {
        log.info("Received product created: {}", event);
        if (event.getData() instanceof InventoryDto inventoryDto) {
            Inventory inventory = Inventory.builder()
                    .productName(inventoryDto.getProductName())
                    .productId(inventoryDto.getProductId())
                    .productPrice(inventoryDto.getProductPrice())
                    .warehouseLocation(inventoryDto.getWarehouseLocation())
                    .build();
            inventoryService.addInventory(inventory);
        }
    }
}
