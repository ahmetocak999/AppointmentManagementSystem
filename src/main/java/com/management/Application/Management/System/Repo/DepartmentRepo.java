package com.management.Application.Management.System.Repo;

import com.management.Application.Management.System.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
}

