package com.harshitkumar.e_commerce_backend.service;

import com.harshitkumar.e_commerce_backend.entity.User;
import com.harshitkumar.e_commerce_backend.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRefreshToken(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    public boolean isPassSame(String dbPass, String password) {
        return passwordEncoder.matches(password, dbPass);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveRefreshToken(Long userId, String refreshToken) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User Not Found!!");
        }

        User user = userOptional.get();
        // user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        try{
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


