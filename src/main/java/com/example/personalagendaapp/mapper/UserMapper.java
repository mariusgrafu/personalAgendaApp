package com.example.personalagendaapp.mapper;

import com.example.personalagendaapp.dto.RegisterRequest;
import com.example.personalagendaapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerRequestToUser (RegisterRequest registerRequest) {
        return new User(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());
    }
}
