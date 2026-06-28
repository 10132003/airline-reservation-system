package com.thomas.airline.user.dto;

import com.thomas.airline.common.enums.RoleName;
import com.thomas.airline.common.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDateTime;

public class UserResponseDto {
    @Schema(
            description = "Unique ID of the user",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "First name of the user",
            example = "John"
    )
    private String firstName;
    @Schema(
            description = "Last name of the user",
            example = "Doe"
    )
    private String lastName;
    @Schema(
            description = "Email address of the user",
            example = "JohnDoe@gmail.com"
    )
    private String email;
    @Schema(
            description = "Phone number of the user",
            example = "1234567890"
    )
    private String phoneNumber;
    @Schema(
            description = "Status of the user",
            example = "ACTIVE"
    )
    private UserStatus status;
    @Schema(
            description = "Role of the user",
            example = "ADMIN"
    )
    private RoleName role;
    @Schema(
            description = "Date and time when the user was created",
            example = "2026-06-28T07:56:53.386Z"
    )
    private LocalDateTime createdAt;
    @Schema(
            description = "Date and time when the user was updated",
            example = "2026-06-29T07:56:53.386Z"
    )
    private LocalDateTime updatedAt;

    public UserResponseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
