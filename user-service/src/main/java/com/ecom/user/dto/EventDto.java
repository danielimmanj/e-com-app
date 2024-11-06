package com.ecom.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EventDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
