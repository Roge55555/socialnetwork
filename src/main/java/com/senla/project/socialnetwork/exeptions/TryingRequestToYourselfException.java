package com.senla.project.socialnetwork.exeptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.CONFLICT)
public class TryingRequestToYourselfException extends RuntimeException {
}
