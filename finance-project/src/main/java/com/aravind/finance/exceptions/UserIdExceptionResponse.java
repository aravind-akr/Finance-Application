package com.aravind.finance.exceptions;

public class UserIdExceptionResponse {

    private String userId;

    public UserIdExceptionResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
