package com.management.Application.Management.System.Repo;

import com.management.Application.Management.System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}
