package com.ecom.inventory.common.config.batch;

import com.ecom.inventory.business.dto.InventoryDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.UUID;

@Configuration
public class InventoryReaderConfig {

    @Bean
    public FlatFileItemReader<InventoryDto> inventoryReader() {
        FlatFileItemReader<InventoryDto> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("input/inventory.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<InventoryDto> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {{
            setNames("productId", "productName", "productPrice", "quantity", "warehouseLocation");
            setDelimiter(",");
        }});

        lineMapper.setFieldSetMapper(fieldSet -> {
            InventoryDto dto = new InventoryDto();
            String id = fieldSet.readString("productId");
            if (id.isBlank()) {
                dto.setProductId(UUID.randomUUID()); // new product registered
            } else {
                try {
                    dto.setProductId(UUID.fromString(id));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid UUID format at line with value: " + id);
                    System.err.println(e.getMessage());
                    dto.setProductId(null);
                }
            }
            dto.setProductName(fieldSet.readString("productName"));
            dto.setProductPrice(fieldSet.readBigDecimal("productPrice"));
            dto.setQuantity(fieldSet.readInt("quantity"));
            dto.setWarehouseLocation(fieldSet.readString("warehouseLocation"));
            return dto;
        });

        reader.setLineMapper(lineMapper);
        return reader;
    }
}

