package com.ecom.api.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user-service")
    public ResponseEntity<String> fallbackUserService() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("User Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/order-service")
    public ResponseEntity<String> fallbackOrderService() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Order Service is currently unavailable. Please try again later.");
    }
}

