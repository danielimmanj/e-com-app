package com.ecom.inventory;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    public static void main(Integer[] args) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter("input/inventory.csv"))) {
            writer.println("productId,productName,productPrice,quantity,warehouseLocation");

            Random random = new Random();

            for (int i = 0; i < 1_000_000; i++) {
                String productId = UUID.randomUUID().toString();
                String productName = "Product-" + i;
                BigDecimal productPrice = BigDecimal.valueOf(10 + random.nextDouble() * 990).setScale(2, RoundingMode.HALF_UP);
                int quantity = random.nextInt(1000);
                String location = "Warehouse-" + (random.nextInt(5) + 1);

                // 2% BAD DATA
                if (i % 50 == 0) {
                    int badType = random.nextInt(4);
                    switch (badType) {
                        case 0 -> productName = ""; // Empty product name
                        case 1 -> productPrice = BigDecimal.valueOf(-random.nextDouble() * 100); // Negative price
                        case 2 -> productId = ""; // Missing UUID
                        case 3 -> productId = "not-a-uuid"; // Malformed UUID
                    }
                }

                writer.printf("%s,%s,%s,%d,%s%n",
                        productId,
                        productName,
                        productPrice.toPlainString(),
                        quantity,
                        location
                );
            }

            System.out.println("📄 CSV with bad records generated at input/inventory.csv");
        }
    }
}
