package io.training.catalyte.sportsapparel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Username and/or password is incorrect.")
public class LoginError extends Exception {

}
