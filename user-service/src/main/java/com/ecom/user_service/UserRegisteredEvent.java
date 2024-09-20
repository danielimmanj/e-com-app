package com.ecom.user_service;

import lombok.Data;
import java.util.UUID;

@Data
public class UserRegisteredEvent {
    private UUID userId;
    private String username;
    private String email;
}