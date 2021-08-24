package com.senla.project.socialnetwork.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)//TODO дважды ставлю статус?! это везде
public class NoNecessaryFieldInSpecificationException extends RuntimeException {
    private String message;

    public NoNecessaryFieldInSpecificationException(String message) {
        this.message = message;
    }

}
