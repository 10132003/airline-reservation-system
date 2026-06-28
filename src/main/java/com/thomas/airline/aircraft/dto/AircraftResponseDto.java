package com.thomas.airline.aircraft.dto;

import com.thomas.airline.common.enums.AircraftStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class AircraftResponseDto {
    @Schema(
            description = "Aircraft's unique ID",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "Aircraft's registration number",
            example = "VT-ANB"
    )
    private String registrationNumber;
    @Schema(
            description = "Name of the aircraft's manufacturer",
            example = "Airbus"
    )
    private String manufacturer;
    @Schema(
            description = "Aircraft's model number",
            example = "A380-800"
    )
    private String model;
    @Schema(
            description = "Status of the aircraft",
            example = "ACTIVE"
    )
    private AircraftStatus status;
    @Schema(
            description = "Date and Time the aircraft was created at",
            example = "2026-06-26 15:08:59.350586"
    )
    private LocalDateTime createdAt;
    @Schema(
            description = "Date and Time the aircraft was updated at",
            example = "2026-06-28 15:08:59.350586"
    )
    private LocalDateTime updatedAt;

    public AircraftResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public AircraftStatus getStatus() {
        return status;
    }

    public void setStatus(AircraftStatus status) {
        this.status = status;
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
