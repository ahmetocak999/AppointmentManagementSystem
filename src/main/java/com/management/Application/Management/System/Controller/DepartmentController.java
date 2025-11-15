package com.management.Application.Management.System.Controller;

import com.management.Application.Management.System.DTO.DepartmentDTO;
import com.management.Application.Management.System.Service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/allDepartments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(){
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable int id){
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO dto){
        return ResponseEntity.ok(departmentService.createDepartment(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id){
        departmentService.deleteDepartments(id);
        return ResponseEntity.ok("Department deleted successfully");
    }

}
