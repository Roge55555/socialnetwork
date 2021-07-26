package com.senla.project.socialnetwork.exeptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
  
    @Value(value = "${data.exception.notOldPassword:Error old password}")
    private String notOldPasswordMessage;
  
    @Value(value = "${data.exception.noSuchIdMessage:No such id - }")
    private String noSuchIdMessage;
 
    @Value(value = "${data.exception.noSuchLoginMessage:No such }")
    private String noSuchLoginMessage;
    
    @Value(value = "${data.exception.dataAlreadyTakenMessage:This login/email/phone is already in use, try another.}")
    private String dataAlreadyTakenMessage;
    
    @Value(value = "${data.exception.elementAlreadyTakenMessage:Such element already exist.}")
    private String elementAlreadyTakenMessage;
    
    @Value(value = "${data.exception.tryingRequestToYourselfMessage:You can`t do it to yourself.}")
    private String tryingRequestToYourselfMessage;

    @Value(value = "${data.exception.JWTInvalidTokenMessage:Your authentication is invalid.}")
    private String JWTInvalidTokenMessage;

    @ExceptionHandler(value = NotOldPasswordException.class)
    public ResponseEntity<String> NotOldPassword(NotOldPasswordException notOldPasswordException) {
        return new ResponseEntity<>(notOldPasswordMessage, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<String> NoSuchElement(NoSuchElementException noSuchElementException) {
        if (noSuchElementException.getLogin() == null) {
            return new ResponseEntity<>(noSuchIdMessage + noSuchElementException.getId(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(noSuchLoginMessage + noSuchElementException.getLogin(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataAlreadyTakenException.class) //TODO нужны и дата и элемент?!
    public ResponseEntity<String> DataAlreadyTaken(DataAlreadyTakenException dataAlreadyTakenException) {
        return new ResponseEntity<>(dataAlreadyTakenMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class) //TODO нужны и дата и элемент?!
    public ResponseEntity<String> ElementAlreadyTaken(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        return new ResponseEntity<>(elementAlreadyTakenMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TryingRequestToYourselfException.class)
    public ResponseEntity<String> TryingRequestToYourself(TryingRequestToYourselfException tryingRequestToYourselfException) {
        return new ResponseEntity<>(tryingRequestToYourselfMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    public ResponseEntity<String> JWTInvalidToken(JwtAuthenticationException jwtAuthenticationException) {
        return new ResponseEntity<>(JWTInvalidTokenMessage, HttpStatus.GONE);
    }
}