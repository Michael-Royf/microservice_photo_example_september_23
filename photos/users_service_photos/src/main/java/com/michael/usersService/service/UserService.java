package com.michael.usersService.service;

import com.michael.usersService.payload.request.UserRequest;
import com.michael.usersService.payload.response.MessageResponse;
import com.michael.usersService.payload.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long userId);

    UserResponse getUserByUsername(String username);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getAllUsers();

    MessageResponse deleteUserById(Long userId);

    UserResponse updateUser(Long userId, UserRequest userRequest);

}
