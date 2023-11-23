package com.builtinaweekendapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email must not be empty")
    @Email(message = "Invalid email!")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "Password must not be empty")
    private String password;

}
