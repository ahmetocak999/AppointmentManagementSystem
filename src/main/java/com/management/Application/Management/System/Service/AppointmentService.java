package com.management.Application.Management.System.Service;

import com.management.Application.Management.System.Repo.AppointmentRepo;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;


    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }


}
