package com.myproject.socialnetwork.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TryingModifyNotYourDataException extends RuntimeException {

    private String message;

    public TryingModifyNotYourDataException(String message) {
        this.message = message;
    }

}
