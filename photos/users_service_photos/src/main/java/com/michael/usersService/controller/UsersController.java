package com.michael.usersService.controller;

import com.michael.usersService.payload.request.UserRequest;
import com.michael.usersService.payload.response.MessageResponse;
import com.michael.usersService.payload.response.UserResponse;
import com.michael.usersService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UsersController {


    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), CREATED);
    }

    @GetMapping("/get/userId/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), OK);
    }

    @GetMapping("/get/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), OK);
    }


    @GetMapping("/get/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable(name = "email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), OK);
    }

    @GetMapping("/get/allUser")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), OK);
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<MessageResponse> deleteUserById(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(userService.deleteUserById(userId), OK);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(name = "userId") Long userId,
                                                   @Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.updateUser(userId, userRequest), OK);
    }


}
