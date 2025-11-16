package com.management.Application.Management.System.Exception;

import com.management.Application.Management.System.Entity.Department;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(String message){
        super(message);
    }
}
