package com.example.personalagendaapp.dto;

import javax.validation.constraints.*;

public class LoginRequest {
    @NotNull
    @NotBlank
    @Size(max = 100)
    @Email(message="Email address must be valid")
    private String email;
    @NotNull(message="Password must be provided")
    @NotBlank(message="Password cannot be empty")
    @Pattern(
            regexp = com.example.personalagendaapp.model.Pattern.PASSWORD,
            message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character"
    )
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
