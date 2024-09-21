package com.ecom.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.UUID;

@Data
public class UserRegisteredEvent {

    @JsonProperty("userId")   // Optional: Customize the JSON key name
    private UUID userId;

    @JsonProperty("username")  // Optional: Customize the JSON key name
    private String username;

    @JsonProperty("email")     // Optional: Customize the JSON key name
    private String email;

    // No need for a default constructor if using @Data
}
