package com.grex.common.exception.types;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }

}
