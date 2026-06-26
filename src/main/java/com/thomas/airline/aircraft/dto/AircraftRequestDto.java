package com.thomas.airline.aircraft.dto;

import jakarta.validation.constraints.NotBlank;

public class AircraftRequestDto {
    @NotBlank
    private String registrationNumber;
    @NotBlank
    private String manufacturer;
    @NotBlank
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
