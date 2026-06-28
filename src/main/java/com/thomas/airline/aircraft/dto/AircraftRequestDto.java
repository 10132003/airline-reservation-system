package com.thomas.airline.aircraft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AircraftRequestDto {
    @Schema(
            description = "Aircraft registration number",
            example = "VT-ANB"
    )
    @NotBlank
    private String registrationNumber;
    @NotBlank
    @Schema(
            description = "Aircraft's manufacturer",
            example = "Airbus"
    )
    private String manufacturer;
    @NotBlank
    @Schema(
            description = "Aircraft's model number",
            example = "A380-800"
    )
    private String model;

    public AircraftRequestDto() {

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
}
