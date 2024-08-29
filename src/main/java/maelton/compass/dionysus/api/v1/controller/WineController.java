package maelton.compass.dionysus.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import maelton.compass.dionysus.api.v1.exception.handler.ExceptionResponse;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineResponseDTO;
import maelton.compass.dionysus.api.v1.service.WineService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wines")
@Tag(name = "Wine Registries Management", description = "Endpoint for managing wine registries")
//@SecurityRequirement(name = "jwtAuthentication")
public class WineController {
    private final WineService service;
    public WineController(WineService wineService) {
        this.service = wineService;
    }

    //CREATE
    @Operation(summary = "Creates a new wine registry", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "New wine registry created successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WineResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                         description = "Missing or invalid fields on the request",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Wine model ID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
        }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<WineResponseDTO> createWine(@Valid @RequestBody WineRequestDTO wineCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createWine(wineCreateDTO));
    }

    //READ ALL
    @Operation(summary = "Retrieves all wine registries", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "All wine registries returned",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = WineResponseDTO.class)
                            )
                    }
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<WineResponseDTO>> getAllWines() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllWines());
    }

    //READ BY ID
    @Operation(summary = "Retrieves a wine registry by its ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Wine registry returned successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WineResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Wine UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
        }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<WineResponseDTO> getWineById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getWineById(id));
    }

    //UPDATE
    @Operation(summary = "Updates a wine registry by its ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Wine registry updated successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WineResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400",
                         description = "Missing or invalid fields on the request",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Wine UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<WineResponseDTO> updateWine(@PathVariable UUID id, @Valid @RequestBody WineRequestDTO wineUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updatedWine(id, wineUpdateDTO));
    }

    //DELETE
    @Operation(summary = "Deletes a wine registry by its ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                         description = "Wine registry deleted successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WineResponseDTO.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Wine UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWine(@PathVariable UUID id) {
        service.deleteWine(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
