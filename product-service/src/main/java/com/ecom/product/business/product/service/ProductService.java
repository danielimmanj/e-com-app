package com.ecom.product.business.product.service;

import com.ecom.product.business.product.model.Product;
import com.ecom.product.business.product.repository.ProductRepository;
import com.ecom.product.common.config.dto.EventDto;
import com.ecom.product.common.config.dto.InventoryDto;
import com.ecom.product.common.config.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final KafkaProducer kafkaProducer;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getProductsByCategoryId(UUID categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product createProduct(Product product) {
        Product productEntity = productRepository.save(product);
        InventoryDto inventoryDto = InventoryDto.builder()
                .productId(productEntity.getId())
                .productPrice(productEntity.getPrice())
                .productName(productEntity.getName())
                .quantity(10)
                .warehouseLocation("India")
                .build();
        EventDto<InventoryDto> event = EventDto.<InventoryDto>builder()
                .data(inventoryDto)
                .build();
        log.info("Event data - {}", event);
        kafkaProducer.sendEvent(KafkaProducer.PRODUCT_CREATED_TOPIC, event);
        return productEntity;
    }

    public Product updateProduct(UUID id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrls(productDetails.getImageUrls());

        Product updatedProduct = productRepository.save(product);

        // Send event to Kafka for product update
        EventDto<Product> event = EventDto.<Product>builder()
                .data(updatedProduct)
                .build();
        kafkaProducer.sendEvent(KafkaProducer.PRODUCT_UPDATED_TOPIC, event);

        return updatedProduct;
    }

    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);

        // Send event to Kafka for product deletion
        EventDto<Product> event = EventDto.<Product>builder()
                .data(product)
                .build();
        kafkaProducer.sendEvent(KafkaProducer.PRODUCT_DELETED_TOPIC, event);
    }

    public List<Product> saveAllProducts(List<Product> products) {
        List<Product> savedProducts = productRepository.saveAll(products);

        // Optional: Send event for each product created if needed
        for (Product product : savedProducts) {
            EventDto<Product> event = EventDto.<Product>builder()
                    .data(product)
                    .build();
            kafkaProducer.sendEvent(KafkaProducer.PRODUCT_CREATED_TOPIC, event);
        }

        return savedProducts;
    }
}
