package com.example.personalagendaapp.dto;

import javax.validation.constraints.*;

public class RegisterRequest extends LoginRequest {
    @NotNull(message="Name must be provided")
    @NotBlank(message="Name cannot be empty")
    @Size(min=3, max = 100)
    private String name;

    public RegisterRequest() {
        super();
    }

    public RegisterRequest(String name, String email, String password) {
        super(email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
