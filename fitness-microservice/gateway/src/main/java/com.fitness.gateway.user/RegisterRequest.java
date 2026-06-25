package com.fitness.gateway.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email required")
    @Email
    private String email;
    @NotNull(message = "password required")
    @Size(min = 6 , message = "password should be atleast 6 characters long")
    @NotNull(message = "password required")
    private String password;
    private String keyClockId;
    private String firstName;
    private String lastName;
}
