package com.ecom.security.business.service;

import com.ecom.security.common.config.CustomProperties;
import com.ecom.security.common.config.dto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

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
                .baseUrl(keycloak.getAuthServerUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    public Mono<String> generateAccessToken(String username, String password) {
        log.info("Token request for {} initiated", username);
        return webClient.post()
                .uri("/token")
                .bodyValue("grant_type=password&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&username=" + username +
                        "&password=" + password)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> generateAdminToken(String username, String password) {
        log.info("Token request for admin user {} initiated", username);
        return webClient.post()
                .uri("http://localhost:8081/realms/master/protocol/openid-connect/token")
                .bodyValue("grant_type=password&client_id=" + "admin-cli" +
                        "&username=" + username +
                        "&password=" + password)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> refreshToken(String refreshToken) {
        return webClient.post()
                .uri("/token")
                .bodyValue("grant_type=refresh_token&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&refresh_token=" + refreshToken)
                .retrieve()
                .bodyToMono(String.class);
    }

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

    public Mono<ResponseEntity<String>> createUser(String token, UserDto userDto) {
        log.info("Creating user with username: {}", userDto);
        log.info("Token: {}", token);

        return webClient.post()
                .uri("http://localhost:8081/admin/realms/e-commerce/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Failed to create user: {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString()));
                });
    }
}
