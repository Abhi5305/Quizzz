package com.exam.quiz.service;

import com.exam.quiz.dto.UserResponse;
import com.exam.quiz.model.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(User user);
    void deleteUserByUserName(Long userId);
    List<User> getUsers();
    User getUserByUserName(String username);
}
