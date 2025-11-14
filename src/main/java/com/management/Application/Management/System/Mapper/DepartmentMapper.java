package com.management.Application.Management.System.Mapper;

import com.management.Application.Management.System.DTO.DepartmentDTO;
import com.management.Application.Management.System.Entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public Department toEntity(DepartmentDTO dto){
        if(dto == null){
            return null;
        }
        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());

        return department;
    }
    public DepartmentDTO toDTO(Department department){
        if(department == null){
            return null;
        }
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());

        return departmentDTO;
    }
}
