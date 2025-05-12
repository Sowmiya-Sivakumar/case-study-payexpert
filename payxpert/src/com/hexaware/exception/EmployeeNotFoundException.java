package com.hexaware.exception;

import java.io.Serializable;

public class EmployeeNotFoundException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

