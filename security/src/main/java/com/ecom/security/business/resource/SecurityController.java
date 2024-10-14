package com.ecom.security.business.resource;

import com.ecom.security.business.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestParam String username, @RequestParam String password) {
        return securityService.generateAccessToken(username, password)
                .map(token -> ResponseEntity.ok().body(token))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/refresh-token")
    public Mono<ResponseEntity<String>> refreshToken(@RequestParam String refreshToken) {
        return securityService.refreshToken(refreshToken)
                .map(token -> ResponseEntity.ok().body(token))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<Object>> logout(@RequestParam String refreshToken) {
        return securityService.logout(refreshToken);
    }
}
