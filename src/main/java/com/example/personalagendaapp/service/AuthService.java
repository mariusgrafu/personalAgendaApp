package com.example.personalagendaapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.personalagendaapp.exception.BadAuthException;
import com.example.personalagendaapp.exception.DuplicateEmailException;
import com.example.personalagendaapp.exception.UserBadLoginException;
import com.example.personalagendaapp.model.TokenKey;
import com.example.personalagendaapp.model.User;
import com.example.personalagendaapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final Algorithm jwtAlgorithm = Algorithm.HMAC256(TokenKey.SECRET_KEY.getBytes());
    private final JWTVerifier jwtVerifier = JWT.require(jwtAlgorithm).build();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        Optional<User> userWithSameEmail = userRepository.getUserByEmail(user.getEmail());
        if (userWithSameEmail.isPresent()) {
            throw new DuplicateEmailException();
        }

        return userRepository.createUser(user);
    }

    public String login(String email, String password) {
        Optional<User> optionalUser = userRepository.getUserByEmailAndPassword(email, password);

        if (!optionalUser.isPresent()) {
            throw new UserBadLoginException();
        }

        return generateTokenWithUserId(optionalUser.get().getId());
    }

    public long validateAuthHeader(String authHeader) {
        // AuthToken comes as "Bearer <actual_token>"
        String token = authHeader.substring(7);
        return validateToken(token);
    }

    private String generateTokenWithUserId(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .sign(jwtAlgorithm);
    }

    private long validateToken(String token) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return Long.parseLong(decodedJWT.getSubject());
        } catch (JWTVerificationException e){
            throw new BadAuthException();
        }
    }
}
