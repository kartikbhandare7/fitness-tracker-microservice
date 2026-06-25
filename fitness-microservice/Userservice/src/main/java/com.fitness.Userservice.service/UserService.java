package com.fitness.Userservice.service;

import com.fitness.Userservice.UserRepository;
import com.fitness.Userservice.dto.RegisterRequest;
import com.fitness.Userservice.dto.UserResponse;
import com.fitness.Userservice.models.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserResponse register(RegisterRequest request) {

        if(repository.existsByEmail(request.getEmail())){
            User existingUser = repository.findByEmail(request.getEmail());
            UserResponse userResponse = new UserResponse();

            userResponse.setEmail(existingUser.getEmail());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setId(existingUser.getId());
            userResponse.setCreatedAt(LocalDate.from(existingUser.getCreatedAt()));
            userResponse.setUpdatedAt(LocalDate.from(existingUser.getUpdatedAt()));
            return userResponse;
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setKeyClockId(request.getKeyClockId());
        User saveUser = repository.save(user);
        UserResponse userResponse = new UserResponse();

        userResponse.setEmail(saveUser.getEmail());
        userResponse.setPassword(saveUser.getPassword());
        userResponse.setFirstName(saveUser.getFirstName());
        userResponse.setKeyClockId(saveUser.getKeyClockId());
        userResponse.setLastName(saveUser.getLastName());
        userResponse.setId(saveUser.getId());
        userResponse.setCreatedAt(LocalDate.from(saveUser.getCreatedAt()));
        userResponse.setUpdatedAt(LocalDate.from(saveUser.getUpdatedAt()));
        return userResponse;

    }

    public UserResponse getUserProfile(String userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = new UserResponse();

        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setId(user.getId());
        userResponse.setCreatedAt(LocalDate.from(user.getCreatedAt()));
        userResponse.setUpdatedAt(LocalDate.from(user.getUpdatedAt()));
        return userResponse;
    }

    public Boolean existsByUserId(String userId) {
        return repository.existsBykeyClockId(userId);
    }
}
