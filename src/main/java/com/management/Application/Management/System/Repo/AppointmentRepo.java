package com.management.Application.Management.System.Repo;

import com.management.Application.Management.System.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {

}
