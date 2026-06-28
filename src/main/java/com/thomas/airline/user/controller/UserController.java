package com.thomas.airline.user.controller;

import com.thomas.airline.user.dto.UserRequestDto;
import com.thomas.airline.user.dto.UserResponseDto;
import com.thomas.airline.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag (
        name = "User Management",
        description = "APIs for managing airline users"
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @Operation(
            summary = "Creates a new user.",
            description = "Creates a new airline user and stores the information in the users table."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto=userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Get all the users from the users table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> responseDtos=userService.getAllUsers();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Get user using unique user id from the user table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<UserResponseDto> getUserById(@Parameter(
            description = "Unique user id",
            required = true,
            example = "1"
    ) @PathVariable Long id){
        UserResponseDto responseDto=userService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update the user",
            description = "Update the user using unique user ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found with the specific ID"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<UserResponseDto> updateUser(@Parameter(
            description = "Unique user ID",
            example = "1",
            required = true
    ) @PathVariable Long id,@Valid @RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto=userService.updateUser(id,requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete the user",
            description = "Delete the user from the user table using the unique user ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<String> deleteUser(@Parameter(
            description = "Unique user ID",
            example = "1",
            required = true
    )@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.ok("User Deleted Successfully.");
    }
}
