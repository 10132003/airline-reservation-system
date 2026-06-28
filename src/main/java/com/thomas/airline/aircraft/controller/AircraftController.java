package com.thomas.airline.aircraft.controller;

import com.thomas.airline.aircraft.dto.AircraftRequestDto;
import com.thomas.airline.aircraft.dto.AircraftResponseDto;
import com.thomas.airline.aircraft.service.AircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aircrafts")
@Tag(
        name = "Aircraft Management",
        description = "APIs for managing the aircrafts"
)
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }
    @PostMapping
    @Operation(
            summary = "Creates new aircraft",
            description = "Creates new aircraft and store in the aircrafts table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Aircraft created successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    }
    )
    public ResponseEntity<AircraftResponseDto> createAircraft(@Valid @RequestBody AircraftRequestDto requestDto){
        AircraftResponseDto responseDto=aircraftService.createAircraft(requestDto);
        return ResponseEntity.status(201).body(responseDto);
    }
    @GetMapping
    @Operation(
            summary = "Get all aircrafts",
            description = "Get all aircrafts from the aircrafts table"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircrafts retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<AircraftResponseDto>> getAllAircrafts(){
        List<AircraftResponseDto> responseDto=aircraftService.getAllAircrafts();
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get aircraft by ID",
            description = "Get aircraft using unique aircraft ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<AircraftResponseDto> getAircraftById(@Parameter(
            description = "Unique aircrat ID",
            example = "1",
            required = true
    ) @PathVariable Long id){
        AircraftResponseDto responseDto=aircraftService.getAircraftById(id);
        return ResponseEntity.ok(responseDto);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update the aircraft",
            description = "Update the aircraft and using the unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<AircraftResponseDto> updateAircraft(@Parameter(
            description = "Unique aircraft ID",
            example = "1",
            required = true
    )@PathVariable Long id, @Valid @RequestBody AircraftRequestDto requestDto){
        AircraftResponseDto responseDto=aircraftService.updateAircraft(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete the aircraft",
            description = "Delete the aricraft using the unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Aircraft deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aircraft not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<String> deleteAircraft(@Parameter(
            description = "Unique aircraft ID",
            example = "1",
            required = true
    )@PathVariable Long id){
        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok("Aircraft Deleted Successfully.");
    }
}
