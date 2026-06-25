package com.fitness.Userservice.controller;

import com.fitness.Userservice.service.UserService;
import com.fitness.Userservice.dto.RegisterRequest;
import com.fitness.Userservice.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserResponse> getUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/{userId}/validate")
    private ResponseEntity<Boolean> validateUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.existsByUserId(userId));
    }
}
