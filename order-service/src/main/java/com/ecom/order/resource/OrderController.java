package com.ecom.order.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final RestTemplate restTemplate;

    @GetMapping("/user/{mail}")
    public ResponseEntity<Object> getUserDetails(@PathVariable String mail) {
        // Calling User Service via Eureka discovery
        Object user = restTemplate.getForObject("http://user-service/api/v1/users/" + mail, Object.class);
        return ResponseEntity.ok(user);
    }
}

