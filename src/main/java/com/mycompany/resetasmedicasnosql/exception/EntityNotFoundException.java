package com.mycompany.resetasmedicasnosql.exception;

/**
 *
 * @author gatog
 */
public class EntityNotFoundException extends RepositoryException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
