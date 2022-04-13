package com.gostilo.messager.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
