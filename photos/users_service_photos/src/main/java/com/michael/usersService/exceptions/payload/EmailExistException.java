package com.michael.usersService.exceptions.payload;

public class EmailExistException  extends RuntimeException{
    public EmailExistException(String message) {
        super(message);
    }
}
