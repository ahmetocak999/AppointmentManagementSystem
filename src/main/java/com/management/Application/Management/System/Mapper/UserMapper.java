package com.management.Application.Management.System.Mapper;

import com.management.Application.Management.System.DTO.UserDTO;
import com.management.Application.Management.System.Entity.Department;
import com.management.Application.Management.System.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user){
        if(user == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setLocation(user.getLocation());
        userDTO.setPhone(user.getPhone());
        userDTO.setDepartmentId(user.getDepartment().getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }
    public User toEntity(UserDTO userDTO, Department department){
        if(userDTO == null){
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setLocation(userDTO.getLocation());
        user.setPhone(userDTO.getPhone());
        user.setDepartment(department);
        user.setFullName(userDTO.getFullName());

        return user;
    }
}
