package com.grex.exception.types;

public class UserEmailAlreadyExistsException extends RuntimeException{

    public UserEmailAlreadyExistsException(String message){
        super(message);
    }

}
