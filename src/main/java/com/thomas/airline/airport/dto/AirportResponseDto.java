package com.thomas.airline.airport.dto;

import com.thomas.airline.common.enums.AirportStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class AirportResponseDto {
    @Schema(
            description = "Unique ID of the airport",
            example = "1"
    )
    private Long id;
    @Schema(
            description = "Code of the airport",
            example = "MAA"
    )
    private String airportCode;
    @Schema(
            description = "Name of the airport",
            example = "Chennai International Airport "
    )
    private String airportName;
    @Schema(
            description = "City of the airport",
            example = "Chennai"
    )
    private String city;
    @Schema(
            description = "State of the airport",
            example = "TamilNadu"
    )
    private String state;
    @Schema(
            description = "Country of the airport",
            example = "India"
    )
    private String country;
    @Schema(
            description = "Timezone of the airport",
            example = "Asia/Kolkata"
    )
    private String timezone;
    @Schema(
            description = "Status of the airport",
            example = "ACTIVE"
    )
    private AirportStatus status;
    @Schema(
            description = "Date and Time the airport was created at",
            example = "2026-06-25 23:20:01.996767"
    )
    private LocalDateTime createdAt;
    @Schema(
            description = "Date and Time the airport was updated at",
            example = "2026-06-27 23:20:01.996767"
    )
    private LocalDateTime updatedAt;

    public AirportResponseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public AirportStatus getStatus() {
        return status;
    }

    public void setStatus(AirportStatus status) {
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
