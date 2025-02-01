package com.exam.quiz.repo;

import com.exam.quiz.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(@NotBlank(message = "User name can't be null/blank") String username);

    boolean existsByEmail(@Email String email);
}
