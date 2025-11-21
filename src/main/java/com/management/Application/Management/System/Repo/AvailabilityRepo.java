package com.management.Application.Management.System.Repo;

import com.management.Application.Management.System.Entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepo extends JpaRepository<Availability,Integer> {
    List<Availability> findByDoctorIdAndDate(int doctorId, LocalDate date);

    List<Availability> findByDoctorId();

    List<Availability> findByPatientIdAndDate(int patientId, LocalDate date);
}
