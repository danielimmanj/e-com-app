package com.ecom.inventory.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDto {

    private UUID id;

    private UUID productId;

    private String productName;

    private BigDecimal productPrice;

    private int quantity;

    private String warehouseLocation;
}

