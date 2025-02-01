package com.exam.quiz.controller;

import com.exam.quiz.dto.AuthRequest;
import com.exam.quiz.dto.ErrorResponse;
import com.exam.quiz.dto.TokenResponse;
import com.exam.quiz.dto.UserResponse;
import com.exam.quiz.exceptions.UserAlreadyExistsException;
import com.exam.quiz.model.User;

import com.exam.quiz.security.MyUserDetailsService;
import com.exam.quiz.service.UserService;
import com.exam.quiz.security.JwtService;
import com.exam.quiz.util.ResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/welcome")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final ResponseGenerator responseGenerator;
    private final String UNEXPECTED_ISSUE = "unexpected issue occured!";
    private final String TECH_ISSUE = "Some technical issue occured!";

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, ResponseGenerator responseGenerator) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.responseGenerator = responseGenerator;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Fetch all users",
            description = "Retrieve all registered users. This API requires admin privileges.",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"User Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unexpected issue occurred", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping("/users/{username}")
    @PreAuthorize("authentication.name == #username and hasAnyRole('USER','ADMIN')")
    @Operation(summary = "fetch users by username", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "500", description = UNEXPECTED_ISSUE, content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = TECH_ISSUE, content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<User> getUserByUserName(@PathVariable("username") String username){
        return ResponseEntity.ok(this.userService.getUserByUserName(username));
    }
    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid User user) throws UserAlreadyExistsException {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    @PostMapping("/generate")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtService.generateToken(authRequest.getUsername());
        TokenResponse tokenResponse = responseGenerator.createTokenResponse(token);
        if(authenticate.isAuthenticated()){
            return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
        }else{
            throw new UsernameNotFoundException("username or password doesn't exists!");
        }
    }
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get the user details and map it to the User object
        User user = (User) myUserDetailsService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "delete user by user id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "500", description = UNEXPECTED_ISSUE, content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = TECH_ISSUE, content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})})
    public void deleteUserByUserName(@PathVariable("userId") Long userId){
         userService.deleteUserByUserName(userId);
    }

}
