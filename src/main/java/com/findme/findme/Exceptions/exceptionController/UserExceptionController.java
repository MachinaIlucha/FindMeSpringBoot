package com.findme.findme.Exceptions.exceptionController;

import com.findme.findme.Exceptions.UserAlreadyAuthorizedException;
import com.findme.findme.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyAuthorizedException.class)
    public ResponseEntity<Object> userAlreadyAuthorizedException() {
        return new ResponseEntity<>("User already authorized", HttpStatus.BAD_REQUEST);
    }
}
