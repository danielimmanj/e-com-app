package com.ecom.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EventDto {

    private UUID userId;
    private String username;
    private String email;
}
