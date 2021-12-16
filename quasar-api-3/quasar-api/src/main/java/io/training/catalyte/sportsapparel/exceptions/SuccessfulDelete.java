package io.training.catalyte.sportsapparel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is used to send a custom error code.
 */
@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Successful DELETE")
public
class SuccessfulDelete extends Exception {

}
