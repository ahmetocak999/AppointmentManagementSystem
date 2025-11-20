package com.management.Application.Management.System.Service;

import com.management.Application.Management.System.DTO.AppointmentDTO;
import com.management.Application.Management.System.Entity.Appointment;
import com.management.Application.Management.System.Exception.AppointmentNotFound;
import com.management.Application.Management.System.Mapper.AppointmentMapper;
import com.management.Application.Management.System.Repo.AppointmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;


    public AppointmentService(AppointmentRepo appointmentRepo, AppointmentMapper appointmentMapper) {
        this.appointmentRepo = appointmentRepo;
        this.appointmentMapper = appointmentMapper;
    }
    public Appointment validateAppointment(int id){
        Optional<Appointment> optionalAvailability = appointmentRepo.findById(id);
        if (optionalAvailability.isEmpty()) {
            throw new AppointmentNotFound("Appointment Not Found: " + id);
        }

    }


    public AppointmentDTO getById(int id) {
        Appointment appointment = validateAppointment(id);
        return appointmentMapper.toDTO(appointment);
    }

    public List<AppointmentDTO> getByDoctorId(int doctorId) {
    }

    public AppointmentDTO createAppointment(AppointmentDTO dto) {
    }

    public List<AppointmentDTO> getByPatientId(int patientId) {
    }
}
