package com.management.Application.Management.System.Exception;

public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException(String message){
        super(message);
    }
}
