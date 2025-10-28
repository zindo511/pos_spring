package com.vn.pos.exception.Custom;

public class PosException extends RuntimeException{
    public PosException(String message) {
        super(message);
    }

    public PosException(String message, Throwable cause) {
        super(message, cause);
    }
}
