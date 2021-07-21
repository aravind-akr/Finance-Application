package com.aravind.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ExpenseException extends RuntimeException {
    public ExpenseException(String message) {
        super(message);
    }
}
