package com.management.Application.Management.System.Exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {

        super(message);
    }
}
