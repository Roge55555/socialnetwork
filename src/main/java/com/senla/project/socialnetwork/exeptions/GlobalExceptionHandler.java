package com.senla.project.socialnetwork.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    //TODO    @Value(value = "${data.exception.message1}")
    //TODO    @Value(value = "${data.exception.message1:Error old password}")
    //private String message1;
    //TODO change names message* -> ...
    private String message1 = "Error old password.";
    //TODO    @Value(value = "${data.exception.message2}")
    private String message2 = "No such id - ";
    //TODO    @Value(value = "${data.exception.message22}")
    private String message22 = "No such ";
    //TODO    @Value(value = "${data.exception.message222}")
    private String message222 = "No such email - ";
    //TODO    @Value(value = "${data.exception.message2222}")
    private String message2222 = "No such phone - ";
    //TODO    @Value(value = "${data.exception.message3}")
    private String message3 = "Account list is empty.";
    //TODO    @Value(value = "${data.exception.message3}")
    private String message4 = "This login/email/phone is already in use, try another.";
    //TODO    @Value(value = "${data.exception.message3}")
    private String message5 = "Such element already exist.";
    //TODO    @Value(value = "${data.exception.message3}")
    private String message6 = "You can`t do it to yourself.";

    @ExceptionHandler(value = NotOldPasswordException.class)
    public ResponseEntity NotOldPasswordException(NotOldPasswordException notOldPasswordException) {
        return new ResponseEntity(message1, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity noSuchIdException(NoSuchElementException noSuchElementException) {
        if (noSuchElementException.getLogin() == null) {
            return new ResponseEntity(message2 + noSuchElementException.getId(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(message22 + noSuchElementException.getLogin(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoAccountsException.class)
    public ResponseEntity NoAccountsException(NoAccountsException noAccountsException) {
        return new ResponseEntity(message3, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LoginEmailPhoneAlreadyTakenException.class)
    public ResponseEntity LoginAlreadyTakenException(LoginEmailPhoneAlreadyTakenException loginEmailPhoneAlreadyTakenException) {
        return new ResponseEntity(message4, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
//TODO разобраться с применением исключения вне моего случая
    public ResponseEntity LoginAlreadyTakenException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        return new ResponseEntity(message5, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TryingRequestToYourselfException.class)
    public ResponseEntity LoginAlreadyTakenException(TryingRequestToYourselfException tryingRequestToYourselfException) {
        return new ResponseEntity(message6, HttpStatus.CONFLICT);
    }
}