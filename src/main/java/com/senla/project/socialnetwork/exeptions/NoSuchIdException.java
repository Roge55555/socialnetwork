package com.senla.project.socialnetwork.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchIdException extends Exception {

    public NoSuchIdException(Long id, String object) {
        super(String.format("No such %s id - %s.", object, id));
    }
}
