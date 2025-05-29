package com.ecom.inventory.business.resource;

import com.ecom.inventory.business.service.JobLauncherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("batch")
@RequiredArgsConstructor
public class InventoryBatchController {

    private final JobLauncherService jobLauncherService;

    @PostMapping("/sync-inventory")
    public ResponseEntity<String> syncInventory() {
        try {
            jobLauncherService.launchInventoryJob();
            return ResponseEntity.ok("Inventory sync job started.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to launch job: " + e.getMessage());
        }
    }
}

