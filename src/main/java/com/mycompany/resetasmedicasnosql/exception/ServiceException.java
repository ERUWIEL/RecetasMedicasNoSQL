package com.mycompany.resetasmedicasnosql.exception;

/**
 *
 * @author gatog
 */
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
