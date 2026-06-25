package com.fitness.Userservice;

import com.fitness.Userservice.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByEmail(String email);


    User findByEmail(@NotBlank(message = "Email required") @Email String email);


    Boolean existsBykeyClockId(String userId);
}
