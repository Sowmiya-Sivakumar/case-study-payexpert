package com.hexaware.exception;

import java.io.Serializable;

public class DatabaseConnectionException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
