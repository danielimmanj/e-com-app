package com.ecom.user.dto;

import lombok.Data;

@Data
public class EventDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
