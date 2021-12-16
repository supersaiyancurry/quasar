package io.training.catalyte.sportsapparel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This data already exists.")
public class DataAlreadyExists extends RuntimeException {

}