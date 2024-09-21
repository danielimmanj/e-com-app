package com.ecom.user.service;

import com.ecom.user.config.kafka.KafkaProducer;
import com.ecom.user.dto.EventDto;
import com.ecom.user.model.User;
import com.ecom.user.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public User registerUser(User user) {
        User savedUser = userRepository.save(user);
        // Produce Kafka event
        EventDto event = new EventDto();
        event.setUserId(savedUser.getId());
        event.setUsername(savedUser.getUsername());
        event.setEmail(savedUser.getEmail());
        kafkaProducer.sendUserRegisteredEvent(event);
        return savedUser;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserByMail")
    public User getUserByMail(String mail) {
        return userRepository.findByEmail(mail)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // Fallback method for circuit breaker
    public User fallbackGetUserByMail(String mail, Throwable throwable) {
        System.out.println("Fallback triggered due to: " + throwable.getMessage());
        return new User(); // Return a default user or handle error accordingly
    }
}