package com.grex.common.exception.types;

public class UserNameAlreadyExistsException extends RuntimeException{

    public UserNameAlreadyExistsException(String message){
        super(message);
    }

}
