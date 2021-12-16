package io.training.catalyte.sportsapparel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are unauthorized.")
public class ForbiddenEndpointException extends RuntimeException {

}
