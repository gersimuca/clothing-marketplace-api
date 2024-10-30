package com.gersimuca.cm.common.exception;

import org.springframework.http.HttpStatus;

import static java.lang.String.format;

public class EntityAlreadyExistsException extends BaseException {
    public EntityAlreadyExistsException(Class<?> entityClass, Long id) {
        super(
                format("%s with id %d already exists", entityClass.getSimpleName(), id),
                HttpStatus.CONFLICT,
                ErrorSeverity.WARN);
    }

    public EntityAlreadyExistsException(Class<?> entityClass, String property) {
        super(
                format("%s already exists (%s)", entityClass, property),
                HttpStatus.CONFLICT,
                ErrorSeverity.WARN);
    }
}
