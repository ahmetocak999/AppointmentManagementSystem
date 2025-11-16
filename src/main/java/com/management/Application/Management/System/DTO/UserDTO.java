package com.management.Application.Management.System.DTO;

import com.management.Application.Management.System.Entity.Role;
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

    private Role role;

    private Integer departmentId;

    private String phone;

    private String location;

}
