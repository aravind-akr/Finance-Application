package com.aravind.finance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryException extends RuntimeException {
    public CategoryException(String s) {
        super(s);
    }
}
