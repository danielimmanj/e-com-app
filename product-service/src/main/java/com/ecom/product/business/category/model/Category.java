package com.ecom.product.business.category.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private UUID id;  // UUID for globally unique category identification.

    @Column(nullable = false, unique = true)
    private String name;

    // Getters and Setters
}
