package com.blog.exceptions;

public class ExistingRecordException extends RuntimeException {

    private String email;

    public ExistingRecordException(String email) {
        super(String.format("%s : email id already exists",email));
        this.email = email;
    }
}
