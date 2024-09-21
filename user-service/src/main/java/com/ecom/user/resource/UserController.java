package com.ecom.user.resource;

import com.ecom.user.model.User;
import com.ecom.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{mail}")
    public ResponseEntity<User> getUserByMail(@PathVariable("mail") String mail) {
        return ResponseEntity.ok(userService.getUserByMail(mail));
    }
}