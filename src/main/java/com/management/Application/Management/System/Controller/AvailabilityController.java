package com.management.Application.Management.System.Controller;

import com.management.Application.Management.System.DTO.AvailabilityDTO;
import com.management.Application.Management.System.Entity.AvailabilityStatus;
import com.management.Application.Management.System.Service.AvailabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/create")
    public ResponseEntity<AvailabilityDTO> createAvailability(@RequestBody AvailabilityDTO availabilityDTO){
        AvailabilityDTO created = availabilityService.createAvailability(availabilityDTO);
        return ResponseEntity.ok(created);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<AvailabilityDTO> updateAvailability(@PathVariable int id, @RequestBody AvailabilityDTO availabilityDTO){
        AvailabilityDTO updated = availabilityService.updateAvailability(id,availabilityDTO);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/deactive/{id}")
    public ResponseEntity<String> deactiveAvailability(@PathVariable int id){
        availabilityService.deactiveAvailability(id);
        return ResponseEntity.ok("The availability is deactivated");
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<AvailabilityDTO>> getDoctorAvailability(@PathVariable int id){
        return ResponseEntity.ok(availabilityService.getAvailabilitiesByDoctorId(id));
    }
    @GetMapping("/isDoctorAvailable/{id}")
    public ResponseEntity<String> checkDoctorAvailability(@PathVariable int doctorId, @RequestParam LocalDate date,
                                                          @RequestParam LocalTime startTime,
                                                          @RequestParam LocalTime endTime) {
        AvailabilityStatus status = availabilityService.isDoctorAvailable(doctorId,date,startTime,endTime);

        return ResponseEntity.ok(status == AvailabilityStatus.AVAILABLE
        ? "Uygun"
        :  "Uygun Değil"
                );
    }

}
