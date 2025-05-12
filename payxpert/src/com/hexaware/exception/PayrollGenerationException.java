package com.hexaware.exception;

import java.io.Serializable;

public class PayrollGenerationException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public PayrollGenerationException(String message) {
        super(message);
    }

    public PayrollGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
