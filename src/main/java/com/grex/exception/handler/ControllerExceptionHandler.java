package com.grex.exception.handler;


import com.grex.exception.types.ResourceNotFoundException;
import com.grex.exception.types.UserEmailAlreadyExistsException;
import com.grex.exception.types.UserNameAlreadyExistsException;
import com.grex.dto.ErrorMessage;
import com.grex.dto.GenericMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<GenericMessage> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.NOT_FOUND,new ErrorMessage("Resource Not Found",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<GenericMessage> noResourceFoundException(NoResourceFoundException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.NOT_FOUND,new ErrorMessage("Resource Not Found",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericMessage> globalExceptionHandler(Exception exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.INTERNAL_SERVER_ERROR,new ErrorMessage("Internal Server Error",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericMessage> badCredentialExceptionHandler(BadCredentialsException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.UNAUTHORIZED,new ErrorMessage("Email or Password is Incorrect",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<GenericMessage> accountStatusExceptionHandler(AccountStatusException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.FORBIDDEN,new ErrorMessage("account is blocked or locked",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericMessage> accessDeniedExceptionHandler(AccessDeniedException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.FORBIDDEN,new ErrorMessage("access denied",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<GenericMessage> signatureExceptionHandler(SignatureException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.UNAUTHORIZED,new ErrorMessage("jwt token verification failed",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GenericMessage> expiredJwtExceptionHandler(ExpiredJwtException exception, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.UNAUTHORIZED,new ErrorMessage("jwt token is expired",exception.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<GenericMessage> handleValidationExceptions(HandlerMethodValidationException ex,WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.BAD_REQUEST,new ErrorMessage("Validation Failed",ex.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<GenericMessage> userNameAlreadyExistsExceptions(UserNameAlreadyExistsException ex, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.BAD_REQUEST,new ErrorMessage("Username already exists",ex.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<GenericMessage> userEmailAlreadyExistsExceptions(UserEmailAlreadyExistsException ex, WebRequest request) {
        GenericMessage message = new GenericMessage(HttpStatus.BAD_REQUEST,new ErrorMessage("Email already exists",ex.toString()));
        return new ResponseEntity<GenericMessage>(message, HttpStatus.BAD_REQUEST);
    }


}
