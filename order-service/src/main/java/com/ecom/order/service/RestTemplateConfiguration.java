package com.ecom.order.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    @LoadBalanced  // This enables service discovery via Eureka
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
