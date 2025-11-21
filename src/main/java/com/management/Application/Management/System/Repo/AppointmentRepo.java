package com.management.Application.Management.System.Repo;

import com.management.Application.Management.System.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {

    List<Appointment> findByDoctorId(int doctorId);


    List<Appointment> findByPatientId(int patientId);

    List<Appointment> findByPatientIdAndDate(int patientId, LocalDate date);


}
