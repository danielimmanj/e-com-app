package com.ecom.common.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    private UUID productId;
    private String productName;
    private BigDecimal productPrice;
    private String warehouseLocation;
    private Integer quantity;
}
