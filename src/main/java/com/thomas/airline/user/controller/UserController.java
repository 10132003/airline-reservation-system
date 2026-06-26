package com.thomas.airline.user.controller;

import com.thomas.airline.user.dto.UserRequestDto;
import com.thomas.airline.user.dto.UserResponseDto;
import com.thomas.airline.user.service.UserService;
import jakarta.validation.Valid;
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
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto=userService.createUser(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> responseDtos=userService.getAllUsers();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        UserResponseDto responseDto=userService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto=userService.updateUser(id,requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.ok("User Deleted Successfully.");
    }
}
