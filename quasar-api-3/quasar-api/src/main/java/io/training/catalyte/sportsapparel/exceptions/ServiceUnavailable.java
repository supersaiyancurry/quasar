package io.training.catalyte.sportsapparel.exceptions;

/**
 * The Exception thrown when a request cannot be completed due to a database access error
 */
public class ServiceUnavailable extends RuntimeException {

    public ServiceUnavailable() {
    }

    public ServiceUnavailable(String message) {
        super(message);
    }

    public ServiceUnavailable(Exception e) {
        super(e.getCause());
    }
}

