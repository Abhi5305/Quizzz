package com.exam.quiz.service.impl;

import com.exam.quiz.dto.UserResponse;
import com.exam.quiz.exceptions.UserAlreadyExistsException;
import com.exam.quiz.model.User;

import com.exam.quiz.repo.UserRepository;
import com.exam.quiz.service.UserService;
import com.exam.quiz.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResponseGenerator responseGenerator;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ResponseGenerator responseGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.responseGenerator = responseGenerator;
    }

    public UserResponse createUser(User user) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("username");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("email");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
       return responseGenerator.createResponse(user.getUsername()+" added to the system successfully");

    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUserByUserName(Long userId) {
         userRepository.deleteById(userId);
    }

}
