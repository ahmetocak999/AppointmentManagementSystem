package com.management.Application.Management.System.DTO;

import com.management.Application.Management.System.Entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO{
    private int id;

    private int doctorId;

    private int patientId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    private String note;

    private String location;
}
