package com.ecom.api.gateway.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user-service")
    public ResponseEntity<String> userServiceFallback() {
        return new ResponseEntity<>("User service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/order-service")
    public ResponseEntity<String> orderServiceFallback() {
        return new ResponseEntity<>("Order service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/inventory-service")
    public ResponseEntity<String> inventoryServiceFallback() {
        return new ResponseEntity<>("Inventory service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/product-service")
    public ResponseEntity<String> productServiceFallback() {
        return new ResponseEntity<>("Product service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/shipping-service")
    public ResponseEntity<String> shippingServiceFallback() {
        return new ResponseEntity<>("Shipping service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/payment-service")
    public ResponseEntity<String> paymentServiceFallback() {
        return new ResponseEntity<>("Payment service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
