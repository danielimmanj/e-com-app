package com.ecom.inventory.business.service;

import com.ecom.inventory.business.model.Inventory;
import com.ecom.inventory.common.config.dto.EventDto;
import com.ecom.inventory.common.config.kafka.KafkaProducer;
import com.ecom.inventory.business.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaProducer kafkaProducer;

    public Inventory addInventory(Inventory item) {
        Inventory savedItem = inventoryRepository.save(item);
        EventDto<Inventory> event = new EventDto<>(savedItem);
        kafkaProducer.sendEvent("inventory-updated", event);
        return savedItem;
    }

    public Optional<Inventory> updateInventory(Long id, Inventory item) {
        return inventoryRepository.findById(id).map(existingItem -> {
            existingItem.setProductName(item.getProductName());
            existingItem.setQuantity(item.getQuantity());
            existingItem.setProductPrice(item.getProductPrice());
            Inventory updatedItem = inventoryRepository.save(existingItem);
            EventDto<Inventory> event = new EventDto<>(updatedItem);
            kafkaProducer.sendEvent("inventory-updated", event);
            return updatedItem;
        });
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
        EventDto<Long> event = new EventDto<>(id);
        kafkaProducer.sendEvent("inventory-deleted", event);
    }

    public Optional<Inventory> getInventory(Long id) {
        return inventoryRepository.findById(id);
    }
}
