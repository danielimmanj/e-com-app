//package com.ecom.api.gateway.config;
//
//import com.ecom.api.gateway.exception.CustomKeycloakAuthenticationHandler;
//import com.ecom.api.gateway.exception.RestAccessDeniedHandler;
//import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
//import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private RestAccessDeniedHandler restAccessDeniedHandler;
//
//    @Autowired
//    private CustomKeycloakAuthenticationHandler customKeycloakAuthenticationHandler;
//
//    // Configure the SecurityFilterChain bean
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for all requests
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .anyRequest().permitAll() // Allow all requests (for testing purposes)
//                ).exceptionHandling(exceptionHandling ->
//                        exceptionHandling.accessDeniedHandler(restAccessDeniedHandler)
//                );
//        return http.build();
//    }
//
//    // Create an AuthenticationManager bean using the AuthenticationConfiguration
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    // Disable default role prefix ROLE_
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//    }
//
//    // Use Spring Boot property files instead of default keycloak.json
//    @Bean
//    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }
//
//    // Register authentication strategy for public or confidential applications
//    @Bean
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    // Keycloak authentication exception handler
//    @Bean
//    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
//        KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(authenticationManager);
//        filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
//        filter.setAuthenticationFailureHandler(customKeycloakAuthenticationHandler);
//        return filter;
//    }
//}