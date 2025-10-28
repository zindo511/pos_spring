package com.vn.pos.exception.Custom;

public class DuplicateResourceException extends PosException {
    public DuplicateResourceException(String resource, String field, Object value) {
        super(String.format("%s already exit with %s: %s'" ,resource, field, value));
    }
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
