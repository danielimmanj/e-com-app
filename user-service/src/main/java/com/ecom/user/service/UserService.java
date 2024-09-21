package com.ecom.user.service;

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
        UserRegisteredEvent event = new UserRegisteredEvent();
        event.setUserId(savedUser.getId());
        event.setUsername(savedUser.getUsername());
        event.setEmail(savedUser.getEmail());
        kafkaProducer.sendUserRegisteredEvent(event);
        return savedUser;
    }
}