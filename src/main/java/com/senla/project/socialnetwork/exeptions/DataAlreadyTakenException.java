package com.senla.project.socialnetwork.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DataAlreadyTakenException extends RuntimeException {
}
