package com.ecom.product.business.category.repository;

import com.ecom.product.business.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    // Additional query methods can be defined here if needed
}
