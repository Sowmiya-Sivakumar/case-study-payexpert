package com.hexaware.exception;

import java.io.Serializable;

public class TaxCalculationException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public TaxCalculationException(String message) {
        super(message);
    }

    public TaxCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
