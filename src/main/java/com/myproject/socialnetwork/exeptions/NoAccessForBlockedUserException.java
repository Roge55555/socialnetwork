package com.myproject.socialnetwork.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoAccessForBlockedUserException extends RuntimeException {

    private String message;

    public NoAccessForBlockedUserException(String message) {
        this.message = message;
    }

}
