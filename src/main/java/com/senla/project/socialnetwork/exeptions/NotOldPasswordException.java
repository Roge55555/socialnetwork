package com.senla.project.socialnetwork.exeptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotOldPasswordException extends RuntimeException{
    private String message;
}
