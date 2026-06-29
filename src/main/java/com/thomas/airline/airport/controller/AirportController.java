package com.thomas.airline.airport.controller;

import com.thomas.airline.airport.dto.AirportRequestDto;
import com.thomas.airline.airport.dto.AirportResponseDto;
import com.thomas.airline.airport.service.AirportService;
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
@RequestMapping("/airports")
@Tag(
        name = "Airport Management",
        description = "API's for managing the airport table"
)
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }
    @PostMapping
    @Operation(
            summary = "Create new airport",
            description = "Create new airport and store in the airport table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Airport created successfully"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Airport already exist"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<AirportResponseDto>  createAirport(@Valid @RequestBody AirportRequestDto requestDto){
        AirportResponseDto responseDto=airportService.createAirport(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping
    @Operation(
            summary = "Retrieve all airports",
            description = "Retrieve all airports and display to the user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "All airports retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<AirportResponseDto>> getAllAirports(){
        List<AirportResponseDto> responseDtos=airportService.getAllAirports();
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve airport by ID",
            description = "Retrieve airport from the airport table using unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport Retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airport not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<AirportResponseDto> getAirportById(@Parameter(
            description = "Unique airport ID",
            example = "1",
            required = true
    ) @PathVariable Long id){
        AirportResponseDto responseDto=airportService.getAirportById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update the airport",
            description = "Update the airport using the unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airport not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Airport is already available"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<AirportResponseDto> updateAirport(@Parameter(
            description = "Unique airport ID",
            example = "1",
            required = true
    ) @PathVariable Long id, @Valid @RequestBody AirportRequestDto requestDto){
        AirportResponseDto responseDto=airportService.updateAirport(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete the airport",
            description = "Delete the airport using the unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Airport deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Airport not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<String> deleteAirport(@Parameter(
            description = "Unique airport ID",
            example = "1",
            required = true
    ) @PathVariable Long id){
        airportService.deleteAirport(id);
        return ResponseEntity.ok("Airport Deleted Successfully.");
    }
}
