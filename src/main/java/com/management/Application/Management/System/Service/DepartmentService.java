package com.management.Application.Management.System.Service;

import com.management.Application.Management.System.DTO.DepartmentDTO;
import com.management.Application.Management.System.Entity.Department;
import com.management.Application.Management.System.Mapper.DepartmentMapper;
import com.management.Application.Management.System.Repo.DepartmentRepo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepo departmentRepo;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepo departmentRepo, DepartmentMapper departmentMapper) {
        this.departmentRepo = departmentRepo;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        Department saved = departmentRepo.save(department);
        return departmentMapper.toDTO(saved);
    }

    public DepartmentDTO getDepartmentById(int id){
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department Not Found"));
        return departmentMapper.toDTO(department);
    }

    public List<DepartmentDTO> getAllDepartment() {
        return departmentRepo.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .toList();
    }
    public void deleteDepartments(int id){
        if(!departmentRepo.existsById(id)) {
            throw new RuntimeException("Department Not Found");
        }
        departmentRepo.deleteById(id);
    }


}
