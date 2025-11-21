package com.management.Application.Management.System.Controller;

import com.management.Application.Management.System.DTO.AppointmentDTO;
import com.management.Application.Management.System.Entity.Appointment;
import com.management.Application.Management.System.Service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable int id){
        AppointmentDTO dto = appointmentService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getByDoctorId(@PathVariable int doctorId){
        List<AppointmentDTO> dto = appointmentService.getByDoctorId(doctorId);
        return ResponseEntity.ok(dto);
    }
    @PostMapping("/createAppointment")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto){
        AppointmentDTO appointmentDTO = appointmentService.createAppointment(dto);
        return ResponseEntity.ok(appointmentDTO);
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getByPatientId(@PathVariable int patientId){
        List<AppointmentDTO> dto = appointmentService.getByPatientId(patientId);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id){
        AppointmentDTO deleteById = appointmentService.deleteById(id);
        return ResponseEntity.ok("The appointment deleted.");
    }


}
