package com.senla.project.socialnetwork.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @Value(value = "${data.exception.message1}")
    private String message1 = "Error old password.";
//    @Value(value = "${data.exception.message2}")
    private String message2 = "No such id - ";
    //    @Value(value = "${data.exception.message22}")
    private String message22 = "No such login - ";
    //    @Value(value = "${data.exception.message222}")
    private String message222 = "No such email - ";
    //    @Value(value = "${data.exception.message2222}")
    private String message2222 = "No such phone - ";
//    @Value(value = "${data.exception.message3}")
    private String message3 = "Account list is empty.";
    //    @Value(value = "${data.exception.message3}")
    private String message4 = "This login/email/phone is already in use, try another.";
    //    @Value(value = "${data.exception.message3}")
    private String message5 = "Such element already exist.";
    //    @Value(value = "${data.exception.message3}")
    private String message6 = "You can`t do it to yourself.";

//    @Value(value = "${data.exception.message3}")
//    private String message3 = "Exception";

    @ExceptionHandler(value = NotOldPasswordException.class)
    public ResponseEntity NotOldPasswordException(NotOldPasswordException notOldPasswordException) {
        return new ResponseEntity(message1, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity noSuchIdException(NoSuchElementException noSuchElementException) {
        if(noSuchElementException.getLogin().isEmpty())
            return new ResponseEntity(message2 + noSuchElementException.getId(), HttpStatus.BAD_REQUEST);
        else
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

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)//TODO разобраться с применением исключения вне моего случая
    public ResponseEntity LoginAlreadyTakenException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        return new ResponseEntity(message5, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TryingRequestToYourselfException.class)
    public ResponseEntity LoginAlreadyTakenException(TryingRequestToYourselfException tryingRequestToYourselfException) {
        return new ResponseEntity(message6, HttpStatus.CONFLICT);
    }
}