package com.management.Application.Management.System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;

    private String fullName;

    private String email;

    private String password;

    private String role;

    private int departmentId;

    private String phone;

    private String location;

}
