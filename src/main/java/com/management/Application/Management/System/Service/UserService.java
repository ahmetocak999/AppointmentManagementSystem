package com.management.Application.Management.System.Service;

import com.management.Application.Management.System.DTO.UserDTO;
import com.management.Application.Management.System.Entity.Department;
import com.management.Application.Management.System.Entity.Role;
import com.management.Application.Management.System.Entity.User;
import com.management.Application.Management.System.Exception.*;
import com.management.Application.Management.System.Mapper.UserMapper;
import com.management.Application.Management.System.Repo.DepartmentRepo;
import com.management.Application.Management.System.Repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, DepartmentRepo departmentRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.departmentRepo = departmentRepo;
        this.userMapper = userMapper;
    }

    public User validateUserExists(int id){
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found :" + id);
        }
        return optionalUser.get();
    }

    public User validateDoctor(int id) {
        User user = validateUserExists(id);

        if (user.getRole() != Role.DOCTOR) {
            throw new InvalidRoleException("This user is not doctor: " + id);
        }
        if (!user.isActive()) {
            throw new InvalidRoleException("Doctor is inactive: " + id);
        }
        if (user.getDepartment() == null) {
            throw new InvalidRoleException("Doctor's department is not assigned: " + id);
        }

        return user;
    }
    public User validatePatient(int id) {
        User user = validateUserExists(id);

        if (user.getRole() != Role.PATIENT) {
            throw new InvalidRoleException("This user is not patient: " + id);
        }
        if (!user.isActive()) {
            throw new InvalidRoleException("Patient is inactive: " + id);
        }

        return user;
    }

    private void validateEmail(String email, Integer currentUserId) {
        Optional<User> existing = userRepo.findByEmail(email);

        if (existing.isPresent()) {
            if (currentUserId == null || existing.get().getId() != currentUserId) {
                throw new DuplicateEmailException("This email is already exists: " + email);
            }
        }
    }
    private void validatePhone(String phone, Integer currentUserId) {
        Optional<User> existing = userRepo.findByPhone(phone);

        if (existing.isPresent()) {
            if (currentUserId == null || existing.get().getId() != currentUserId) {
                throw new DuplicatePhoneException("This phone is already exists: " + phone);
            }
        }
    }
    private Department validateRoleAndDepartment(Role role, Integer departmentId) {

        if (role == null) {
            throw new InvalidRoleException("Role cannot be empty.");
        }

        if (role == Role.DOCTOR && departmentId == null) {
            throw new InvalidRoleException("The department has to chosen for doctor.");
        }

        if (departmentId == null) {
            return null;
        }

        return departmentRepo.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("The department not found: " + departmentId));
    }
    public UserDTO createUser(UserDTO userDTO) {

        validateEmail(userDTO.getEmail(), null);
        validatePhone(userDTO.getPhone(), null);

        Department department = validateRoleAndDepartment(userDTO.getRole(), userDTO.getDepartmentId());

        User user = userMapper.toEntity(userDTO, department);
        user.setActive(true);

        User savedUser = userRepo.save(user);

        return userMapper.toDTO(savedUser);
    }
    public User getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    public UserDTO updateUser(int id, UserDTO dto) {

        User user = validateUserExists(id);

        validateEmail(dto.getEmail(), id);
        validatePhone(dto.getPhone(), id);

        Department department = validateRoleAndDepartment(dto.getRole(), dto.getDepartmentId());

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setLocation(dto.getLocation());
        user.setRole(dto.getRole());
        user.setDepartment(department);

        User updated = userRepo.save(user);

        return userMapper.toDTO(updated);
    }

    public List<UserDTO> getAllUsers(){

        return userRepo.findAll()
                .stream().map(userMapper::toDTO)
                .toList();
    }

    public UserDTO getByEmail(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("This email is not found: " + email));

        return userMapper.toDTO(user);
    }

    public void deactivateUser(int id){
        User user = validateUserExists(id);

        if(!user.isActive()){
            throw new RuntimeException("This user is already inactive");
        }
        user.setActive(false);
        userRepo.save(user);
    }

    public List<UserDTO> getActiveUsers(){
        return userRepo.findByActive(true)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }


}
