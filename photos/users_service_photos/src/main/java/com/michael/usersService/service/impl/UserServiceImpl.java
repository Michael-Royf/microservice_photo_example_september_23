package com.michael.usersService.service.impl;

import com.michael.usersService.entity.User;
import com.michael.usersService.exceptions.payload.EmailExistException;
import com.michael.usersService.exceptions.payload.UserNotFoundException;
import com.michael.usersService.exceptions.payload.UsernameExistException;
import com.michael.usersService.payload.request.UserRequest;
import com.michael.usersService.payload.response.MessageResponse;
import com.michael.usersService.payload.response.UserResponse;
import com.michael.usersService.repository.UserRepository;
import com.michael.usersService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.michael.usersService.constant.UserConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        //проверка
        validateNewUsernameAndEmail(
                StringUtils.EMPTY,
                userRequest.getUsername().trim(),
                userRequest.getEmail().trim().toLowerCase());


        User user = User.builder()
                .username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .encryptedPassword(passwordEncoder.encode(userRequest.getPassword()))
                .build();
        user = userRepository.save(user);

        UserResponse userResponse = mapper.map(user, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return mapper.map(getUserFromDBById(userId), UserResponse.class);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = findOptionalUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(NO_USER_FOUND_BY_USERNAME, username)));
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format(NO_USER_FOUND_BY_EMAIL, email)));
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse deleteUserById(Long userId) {
        User user = getUserFromDBById(userId);
        userRepository.delete(user);
        return new MessageResponse(String.format(USER_DELETED, user.getUsername()));
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = getUserFromDBById(userId);
        //проверка
        validateNewUsernameAndEmail(
                user.getUsername(),
                userRequest.getUsername().trim(),
                userRequest.getEmail().trim().toLowerCase());

        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user = userRepository.save(user);
        return mapper.map(user, UserResponse.class);
    }


    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findOptionalUserByUsername(newUsername).orElse(null);
        User userByNewEmail = findUserByEmail(newEmail).orElse(null);
        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findOptionalUserByUsername(currentUsername).orElse(null);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if (userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    private User getUserFromDBById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(NO_USER_FOUND_BY_ID, userId)));
    }

    private Optional<User> findOptionalUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
