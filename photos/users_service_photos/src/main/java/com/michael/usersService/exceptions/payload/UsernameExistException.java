package com.michael.usersService.exceptions.payload;

public class UsernameExistException  extends RuntimeException{
    public UsernameExistException(String message) {
        super(message);
    }
}
