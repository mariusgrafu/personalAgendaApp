package com.example.personalagendaapp.controller;

import com.example.personalagendaapp.dto.LoginRequest;
import com.example.personalagendaapp.dto.RegisterRequest;
import com.example.personalagendaapp.mapper.UserMapper;
import com.example.personalagendaapp.model.User;
import com.example.personalagendaapp.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@Validated
@Api(value = "Auth Controller", description = "REST APIs to register and login on the platform! Login returns an access token which can be used for other calls.")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Registers a new user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "A new user was created"),
            @ApiResponse(code = 400, message = "Bad Request! Failed validation.") })
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User createdUser = authService.register(userMapper.registerRequestToUser(registerRequest));

        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.ok().body(token);
    }
}
