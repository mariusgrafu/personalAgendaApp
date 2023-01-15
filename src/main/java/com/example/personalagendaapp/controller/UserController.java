package com.example.personalagendaapp.controller;

import com.example.personalagendaapp.dto.RegisterRequest;
import com.example.personalagendaapp.mapper.UserMapper;
import com.example.personalagendaapp.model.User;
import com.example.personalagendaapp.service.AuthService;
import com.example.personalagendaapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader String authorization) {
        long userId = authService.validateAuthHeader(authorization);
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(
            @PathVariable long id,
            @Valid @RequestBody RegisterRequest userData
    ) {
        return ResponseEntity.ok()
                .body(userService.editUser(id, userMapper.registerRequestToUser(userData)));
    }
}
