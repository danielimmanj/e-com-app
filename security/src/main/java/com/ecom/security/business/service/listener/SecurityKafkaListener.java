package com.ecom.security.business.service.listener;

import com.ecom.security.business.service.AdminService;
import com.ecom.security.business.service.SecurityService;
import com.ecom.security.common.config.dto.EventDto;
import com.ecom.security.common.config.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityKafkaListener {

    private final SecurityService securityService;
    private final AdminService adminService;

    @KafkaListener(topics = "user-registered", groupId = "security-service-group", containerFactory = "listenerContainerFactory")
    public void handleUserRegisteredEvent(EventDto event) {
        log.info("Received User Registered Event for user: {}", event);
        UserDto userDto = UserDto.builder()
                .username(event.getEmail())
                .firstName(event.getName())
                .lastName(event.getName())
                .email(event.getEmail())
                .emailVerified(true)
                .createdTimestamp(System.currentTimeMillis())
                .enabled(true)
                .totp(false)
                .disableableCredentialTypes(List.of())
                .requiredActions(List.of())
                .notBefore(0)
                .access(
                        UserDto.AccessDto.builder()
                                .manageGroupMembership(true)
                                .view(true)
                                .mapRoles(true)
                                .impersonate(true)
                                .manage(true)
                                .build()
                ).credentials(List.of(UserDto.CredentialsDto.builder()
                        .type("password")
                        .value(event.getPassword())
                        .temporary(false)
                        .build())
                )
                .build();
        adminService.generateAdminToken("admin", "admin")
                .flatMap(token -> {
                    // Ensure the token is non-empty before proceeding
                    if (Objects.isNull(token)) {
                        return Mono.error(new IllegalArgumentException("Failed to obtain access token"));
                    }
                    // Call createUser method with the obtained access token and the user DTO
                    try {
                        return adminService.createUser(token.getAccessToken(), userDto);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe(response -> {
                    // Handle the response from createUser
                    System.out.println("User creation response: " + response);
                }, error -> {
                    // Handle any errors that occurred during the flow
                    System.err.println("Error: " + error.getMessage());
                });
    }
}
