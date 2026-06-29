package com.thomas.airline.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AirportRequestDto {
    @Schema(
            description = "Unique airport code",
            example="MAA"
    )
    @NotBlank
    private String airportCode;
    @Schema(
            description = "Name of the airport",
            example = "Dubai International Airport"
    )
    @NotBlank
    private  String airportName;
    @Schema(
            description = "City of the airport",
            example = "Dubai"
    )
    @NotBlank
    private String city;
    @Schema(
            description = "State of the airport",
            example = "Dubai"
    )
    @NotBlank
    private String state;
    @Schema(
            description = "Country of the airport",
            example = "United Arab Emirates"
    )
    @NotBlank
    private String country;
    @Schema(
            description = "Timezone of the airport",
            example = "Asia/Dubai"
    )
    @NotBlank
    private String timezone;

    public AirportRequestDto() {

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
}
