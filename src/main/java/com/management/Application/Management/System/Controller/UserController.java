package com.management.Application.Management.System.Controller;

import com.management.Application.Management.System.DTO.UserDTO;
import com.management.Application.Management.System.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> user = userService.getAllUsers();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers(){
        List<UserDTO> user = userService.getActiveUsers();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getByEmail(@RequestParam String email){
        UserDTO user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO user = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable int id){
        UserDTO user = userService.updateUser(id,userDTO);
        return ResponseEntity.ok(user);

    }
    @PatchMapping("/deactive/{id}")
    public ResponseEntity<String> deactiveUserById(@PathVariable int id){
        userService.deactivateUser(id);
        return ResponseEntity.ok("The user is deactivated");
    }
}
