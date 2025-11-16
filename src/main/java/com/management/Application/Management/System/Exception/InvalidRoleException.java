package com.management.Application.Management.System.Exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message){
        super(message);
    }
}
