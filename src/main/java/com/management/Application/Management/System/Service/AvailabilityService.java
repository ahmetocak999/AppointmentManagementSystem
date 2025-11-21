package com.management.Application.Management.System.Service;


import com.management.Application.Management.System.DTO.AvailabilityDTO;
import com.management.Application.Management.System.Entity.Availability;
import com.management.Application.Management.System.Entity.AvailabilityStatus;
import com.management.Application.Management.System.Entity.User;
import com.management.Application.Management.System.Exception.AvailabilityConflictException;
import com.management.Application.Management.System.Exception.AvailabilityNotFoundException;
import com.management.Application.Management.System.Exception.InvalidTimeRangeException;
import com.management.Application.Management.System.Mapper.AvailabilityMapper;
import com.management.Application.Management.System.Repo.AvailabilityRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {
    private final AvailabilityRepo availabilityRepo;
    private final UserService userService;
    private final AvailabilityMapper availabilityMapper;


    public AvailabilityService(AvailabilityRepo availabilityRepo, UserService userService, AvailabilityMapper availabilityMapper) {
        this.availabilityRepo = availabilityRepo;
        this.userService = userService;
        this.availabilityMapper = availabilityMapper;
    }
    public Availability validateAvailability(int id){
        Optional<Availability> optionalAvailability = availabilityRepo.findById(id);
        if(optionalAvailability.isEmpty()){
            throw new AvailabilityNotFoundException("Availability Not Found:" + id);
        }
        return optionalAvailability.get();
    }
    public void validateNoTimeConflict(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime){
        userService.validateDoctor(doctorId);

        if(startTime.isAfter(endTime)){
            throw new InvalidTimeRangeException("Start time cannot be after or equal to end time.");
        }

        List<Availability> existing = availabilityRepo.findByDoctorIdAndDate(doctorId, date);

        for (Availability a : existing) {
            boolean startsBeforeNewEnd = a.getStartTime().isBefore(endTime);

            boolean endsAfterNewStart = a.getEndTime().isAfter(startTime);

            if(startsBeforeNewEnd && endsAfterNewStart){
                throw new AvailabilityConflictException("Doctor already has an availability between "
                + a.getStartTime() + "and" + a.getEndTime() + "on date" + date);
            }

        }
    }
    public List<AvailabilityDTO> getAvailabilitiesByDoctorId(int doctorId){
        userService.validateDoctor(doctorId);

        return availabilityRepo.findByDoctorId().stream().map(availabilityMapper::toDTO).toList();
    }

    public AvailabilityDTO createAvailability(AvailabilityDTO availabilityDTO){

        User doctor = userService.getUserById(availabilityDTO.getDoctorId());
        userService.validateDoctor(availabilityDTO.getDoctorId());

        validateNoTimeConflict(availabilityDTO.getDoctorId(),availabilityDTO.getDate(),availabilityDTO.getStartTime(),availabilityDTO.getEndTime());

        Availability availability = availabilityMapper.toEntity(availabilityDTO,doctor);
        Availability saved = availabilityRepo.save(availability);
        return availabilityMapper.toDTO(saved);

    }

    public AvailabilityDTO updateAvailability(int doctorId, AvailabilityDTO availabilityDTO){
        Availability availability = validateAvailability(availabilityDTO.getId());
        User doctor = userService.validateDoctor(doctorId);
        validateNoTimeConflict(doctorId, availabilityDTO.getDate(),availabilityDTO.getStartTime(),availabilityDTO.getEndTime());

        availability.setId(availabilityDTO.getId());
        availability.setActive(availabilityDTO.isActive());
        availability.setStartTime(availabilityDTO.getStartTime());
        availability.setEndTime(availabilityDTO.getEndTime());
        availability.setDate(availabilityDTO.getDate());
        availability.setDoctor(doctor);

        Availability updated = availabilityRepo.save(availability);
        return availabilityMapper.toDTO(updated);
    }

    public void deactiveAvailability(int id){
        Availability availability = validateAvailability(id);
        availability.setActive(false);
        availabilityRepo.save(availability);
    }

    public AvailabilityStatus isDoctorAvailable(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime){
        userService.validateDoctor(doctorId);

        List<Availability> availabilities =
                availabilityRepo.findByDoctorIdAndDate(doctorId, date);

        if (availabilities.isEmpty()) {
            return AvailabilityStatus.UNAVAILABLE;
        }

        for (Availability availability : availabilities) {

            boolean startsBeforeOrAtStart =
                    !availability.getStartTime().isAfter(startTime);

            boolean endsAfterOrAtEnd =
                    !availability.getEndTime().isBefore(endTime);

            if (startsBeforeOrAtStart && endsAfterOrAtEnd) {
                return AvailabilityStatus.AVAILABLE;
            }
        }
        return AvailabilityStatus.UNAVAILABLE;
    }

}

