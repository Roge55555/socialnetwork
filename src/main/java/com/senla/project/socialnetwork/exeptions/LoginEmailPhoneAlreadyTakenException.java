package com.senla.project.socialnetwork.exeptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor

@ResponseStatus(code = HttpStatus.CONFLICT)
public class LoginEmailPhoneAlreadyTakenException extends RuntimeException {
}
