package com.fitness.Userservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email required")
    @Email
    private String email;
    @NotNull(message = "password required")
    @Size(min = 6 , message = "password should be atleast 6 characters long")
    private String password;
    private String keyClockId;
    private String firstName;
    private String lastName;
}
