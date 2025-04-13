// package com.ecom.api.gateway.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.oauth2.client.*;
// import org.springframework.security.oauth2.client.registration.*;
// import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
// import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

// @Configuration
// @EnableWebSecurity
// @EnableOAuth2Client
// public class OAuth2ClientConfig {

//     @Autowired
//     private ClientRegistrationRepository clientRegistrationRepository;

//     @Bean
//     public OAuth2AuthorizedClientManager authorizedClientManager(
//             OAuth2AuthorizedClientRepository authorizedClientRepository) {

//         OAuth2AuthorizedClientProvider authorizedClientProvider =
//                 OAuth2AuthorizedClientProviderBuilder.builder()
//                         .clientCredentials()
//                         .build();

//         AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager =
//                 new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                         clientRegistrationRepository, new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository));

//         clientManager.setAuthorizedClientProvider(authorizedClientProvider);
//         return clientManager;
//     }
// }
