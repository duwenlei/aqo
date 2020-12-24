package com.grape.base.exception;

/**
 * 自定义异常
 *
 * @author duwenlei
 **/
public class GrapeException extends Exception {
    public GrapeException(String message) {
        super(message);
    }

    public GrapeException(String message, Throwable cause) {
        super(message, cause);
    }
}
