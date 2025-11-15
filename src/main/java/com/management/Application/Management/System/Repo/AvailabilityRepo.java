package com.management.Application.Management.System.Repo;

import com.management.Application.Management.System.Entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepo extends JpaRepository<Availability,Integer> {
}
