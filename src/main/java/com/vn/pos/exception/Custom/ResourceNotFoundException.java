package com.vn.pos.exception.Custom;

public class ResourceNotFoundException extends PosException{
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s not found with %s: '%s'", resource, field, value));
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
