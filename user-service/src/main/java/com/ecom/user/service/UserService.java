package com.ecom.user.service;

import com.ecom.user.config.kafka.KafkaProducer;
import com.ecom.user.dto.EventDto;
import com.ecom.user.model.User;
import com.ecom.user.repository.UserRepository;
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

    public User getUserByMail(String mail) {
        return userRepository.findByEmail(mail).orElseThrow();
    }
}