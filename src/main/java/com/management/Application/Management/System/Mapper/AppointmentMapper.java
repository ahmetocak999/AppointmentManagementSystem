package com.management.Application.Management.System.Mapper;

import com.management.Application.Management.System.DTO.AppointmentDTO;
import com.management.Application.Management.System.Entity.Appointment;
import com.management.Application.Management.System.Entity.AppointmentStatus;
import com.management.Application.Management.System.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    public AppointmentDTO toDTO(Appointment appointment){
        if(appointment == null){
            return null;
        }
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setNote(appointment.getNote());
        appointmentDTO.setLocation(appointment.getLocation());
        appointmentDTO.setEndTime(appointment.getEndTime());
        appointmentDTO.setStatus(appointment.getStatus().name());
        appointmentDTO.setDoctorId(appointment.getDoctor().getId());
        appointmentDTO.setPatientId(appointment.getPatient().getId());
        appointmentDTO.setStartTime(appointment.getStartTime());

        return appointmentDTO;
    }

    public Appointment toEntity(AppointmentDTO dto, User doctor, User patient){
        if(dto == null){
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setDate(dto.getDate());
        appointment.setNote(dto.getNote());
        appointment.setLocation(dto.getLocation());
        appointment.setEndTime(dto.getEndTime());
        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        return appointment;
    }
}
