package com.horvee.storylog.core.exceptions;

/**
 * Use to interceptor object throw new exception
 *
 * */
public class InterceptorObjectException extends RuntimeException {

    public InterceptorObjectException(String message) {
        super(message);
    }

    public InterceptorObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
