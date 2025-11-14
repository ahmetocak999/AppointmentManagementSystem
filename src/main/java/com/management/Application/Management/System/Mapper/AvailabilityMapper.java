package com.management.Application.Management.System.Mapper;

import com.management.Application.Management.System.DTO.AvailabilityDTO;
import com.management.Application.Management.System.Entity.Availability;
import com.management.Application.Management.System.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityMapper {

    public AvailabilityDTO toDTO(Availability entity) {
        if (entity == null) {
            return null;
        }
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setId(entity.getId());
        availabilityDTO.setDate(entity.getDate());
        availabilityDTO.setActive(entity.isActive());
        availabilityDTO.setEndTime(entity.getEndTime());
        availabilityDTO.setStartTime(entity.getStartTime());
        availabilityDTO.setDoctorId(entity.getDoctor().getId());

        return availabilityDTO;
    }
    public Availability toEntity(AvailabilityDTO dto, User doctor){
        if(dto == null){
            return null;
        }
        Availability availability = new Availability();
        availability.setId(dto.getId());
        availability.setActive(dto.isActive());
        availability.setDate(dto.getDate());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setDoctor(doctor);

        return availability;
    }
}
