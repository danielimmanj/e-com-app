package com.ecom.security.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SecurityService {

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;
    private final String logoutUrl;

    public SecurityService(
            @Value("${keycloak.auth-server-url}") String authServerUrl,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = authServerUrl + "/token";
        this.logoutUrl = authServerUrl + "/logout";

        this.webClient = WebClient.builder()
                .baseUrl(authServerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    public Mono<String> generateAccessToken(String username, String password) {
        return webClient.post()
                .uri("/token")
                .bodyValue("grant_type=password&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
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

}
