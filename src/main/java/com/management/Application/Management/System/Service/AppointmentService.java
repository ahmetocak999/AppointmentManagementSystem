package com.management.Application.Management.System.Service;

import com.management.Application.Management.System.DTO.AppointmentDTO;
import com.management.Application.Management.System.Entity.*;
import com.management.Application.Management.System.Exception.AppointmentConflictException;
import com.management.Application.Management.System.Exception.AppointmentNotFoundException;
import com.management.Application.Management.System.Exception.DoctorNotAvailableException;
import com.management.Application.Management.System.Exception.InvalidTimeRangeException;
import com.management.Application.Management.System.Mapper.AppointmentMapper;
import com.management.Application.Management.System.Repo.AppointmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;
    private final UserService userService;
    private final AvailabilityService availabilityService;


    public AppointmentService(AppointmentRepo appointmentRepo, AppointmentMapper appointmentMapper, UserService userService, AvailabilityService availabilityService) {
        this.appointmentRepo = appointmentRepo;
        this.appointmentMapper = appointmentMapper;
        this.userService = userService;

        this.availabilityService = availabilityService;
    }
    public Appointment validateAppointment(int id){
        Optional<Appointment> optionalAvailability = appointmentRepo.findById(id);
        if (optionalAvailability.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment Not Found: " + id);
        }
        return optionalAvailability.get();
    }


    public AppointmentDTO getById(int id) {
        Appointment appointment = validateAppointment(id);
        return appointmentMapper.toDTO(appointment);
    }

    public List<AppointmentDTO> getByDoctorId(int doctorId) {
        userService.validateDoctor(doctorId);
        return appointmentRepo.findByDoctorId(doctorId).stream().map(appointmentMapper::toDTO).toList();
    }

    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new InvalidTimeRangeException("Start time and end time must not be null.");
        }

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new InvalidTimeRangeException("End time must be after start time.");
        }

        User patient = userService.validatePatient(dto.getPatientId());

        AvailabilityStatus doctorAvailability =
                availabilityService.isDoctorAvailable(
                        dto.getDoctorId(),
                        dto.getDate(),
                        dto.getStartTime(),
                        dto.getEndTime()
                );

        if (doctorAvailability != AvailabilityStatus.AVAILABLE) {
            throw new DoctorNotAvailableException("Doctor is not available for the requested time slot.");
        }
        List<Appointment> appointments =
                appointmentRepo.findByPatientIdAndDate(dto.getPatientId(), dto.getDate());

        for (Appointment existing : appointments) {
            boolean overlaps =
                    dto.getStartTime().isBefore(existing.getEndTime()) &&
                            dto.getEndTime().isAfter(existing.getStartTime());

            if (overlaps) {
                throw new AppointmentConflictException(
                        "Patient already has an appointment between "
                                + existing.getStartTime() + " and " + existing.getEndTime()
                );
            }
        }
        User doctor = userService.validateDoctor(dto.getDoctorId());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(dto.getDate());
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setNote(dto.getNote());
        appointment.setLocation(dto.getLocation());

        Appointment saved = appointmentRepo.save(appointment);
        return appointmentMapper.toDTO(saved);

    }

    public List<AppointmentDTO> getByPatientId(int patientId) {
        userService.validatePatient(patientId);
        return appointmentRepo.findByPatientId(patientId).stream().map(appointmentMapper::toDTO).toList();

    }

    public AppointmentDTO deleteById(int id) {
        Appointment appointment = validateAppointment(id);
        appointmentRepo.delete(appointment);
        return appointmentMapper.toDTO(appointment);
    }


}
