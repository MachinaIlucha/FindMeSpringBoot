package com.findme.findme.Exceptions.exceptionController;

import com.findme.findme.Exceptions.RelationshipAlreadyExistException;
import com.findme.findme.Exceptions.RelationshipNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RelationshipExceptionController {

    @ExceptionHandler(value = RelationshipAlreadyExistException.class)
    public ResponseEntity<Object> relationshipAlreadyExistException() {
        return new ResponseEntity<>("Relationship already exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RelationshipNotFoundException.class)
    public ResponseEntity<Object> relationshipNotFoundException() {
        return new ResponseEntity<>("Relationship not found", HttpStatus.NOT_FOUND);
    }
}
