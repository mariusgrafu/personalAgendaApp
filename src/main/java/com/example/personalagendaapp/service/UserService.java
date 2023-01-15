package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.UserNotFoundException;
import com.example.personalagendaapp.model.User;
import com.example.personalagendaapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long id) {
        Optional<User> optionalUser = userRepository.getUserById(id);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(id);
        }

        return optionalUser.get();
    }

    public User editUser(long id, User user) {
        userRepository.updateUser(id, user);

        return getUserById(id);
    }
}
