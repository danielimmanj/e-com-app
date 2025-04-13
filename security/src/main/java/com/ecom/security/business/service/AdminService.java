package com.ecom.security.business.service;

import com.ecom.security.common.config.dto.TokenResponseDto;
import com.ecom.security.common.config.CustomProperties;
import com.ecom.security.common.config.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private WebClient webClient;
    private String clientId;
    private String clientSecret;

    private final CustomProperties properties;

    @PostConstruct
    void init() {
        var keycloak = properties.getSpring().getSecurity().getOauth2().getClient().getRegistration().getKeycloak();
        clientId = keycloak.getClientId();
        clientSecret = keycloak.getClientSecret();
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    public Mono<TokenResponseDto> generateAdminToken(String username, String password) {
        log.info("Token request for admin user {} initiated", username);
        return webClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=password" +
                        "&client_id=admin-cli" +
                        "&username=" + username +
                        "&password=" + password)
                .retrieve()
                .bodyToMono(TokenResponseDto.class);
    }

//    TODO
    public Mono<String> refreshToken(String refreshToken) {
        return webClient.post()
                .uri("/token")
                .bodyValue("grant_type=refresh_token&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&refresh_token=" + refreshToken)
                .retrieve()
                .bodyToMono(String.class);
    }

//    TODO
    public Mono<ResponseEntity<Object>> logout(String refreshToken) {
        return webClient.post()
                .uri("/logout")
                .bodyValue("client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&refresh_token=" + refreshToken)
                .retrieve()
                .toBodilessEntity()  // Indicates that we are expecting no body in the response
                .map(response -> ResponseEntity.noContent().build()) // Return 204 No Content if successful
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body("Logout failed: " + error.getMessage())));
    }

    public Mono<ResponseEntity<String>> createUser(String token, UserDto userDto) throws JsonProcessingException {
        log.info("Creating user with username: {}", userDto);

        return webClient.post()
                .uri("/admin/realms/e-commerce/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Failed to create user: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString()));
                });
    }
}
