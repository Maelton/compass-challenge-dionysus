package maelton.compass.dionysus.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import maelton.compass.dionysus.api.v1.exception.handler.ExceptionResponse;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelResponseDTO;
import maelton.compass.dionysus.api.v1.service.WineModelService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/v1/models")
@Tag(name = "Wine Models Management", description = "Endpoint for managing wine models")
@SecurityRequirement(name = "jwtAuthentication")
public class WineModelController {

    private final WineModelService service;
    public WineModelController(WineModelService wineModelService) {
        this.service = wineModelService;
    }

    //CREATE
    @Operation(summary = "Creates a new wine model", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "New wine model created successfully",
                         content = {
                            @Content(
                                 mediaType = "application/json",
                                 schema = @Schema(implementation = WineModelResponseDTO.class)
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
            @ApiResponse(responseCode = "409",
                         description = "Wine model already exists",
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
    public ResponseEntity<WineModelResponseDTO> createWineModel(@Valid @RequestBody WineModelRequestDTO wineModelCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createWineModel(wineModelCreateDTO));
    }

    //READ ALL
    @Operation(summary = "Retrieves all wine models", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "All wine model registries returned",
                         content = {
                            @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "array", implementation = WineModelResponseDTO.class)
                            )
                         }
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<WineModelResponseDTO>> getAllWineModels() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllWineModels());
    }

    //READ BY ID
    @Operation(summary = "Retrieves a wine model by its ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Wine model returned successfully",
                         content = {
                            @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = WineModelResponseDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Wine model not found",
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
    public ResponseEntity<WineModelResponseDTO> getWineModelById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getWineModelById(id));
    }

    //UPDATE
    @Operation(summary = "Updates a wine model by its ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Wine model updated successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WineModelResponseDTO.class)
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
            ),
            @ApiResponse(responseCode = "409",
                         description = "Wine model already exists",
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
    public ResponseEntity<WineModelResponseDTO> updateWineModel(@PathVariable Long id, @Valid @RequestBody WineModelRequestDTO wineModelUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updatedWineModel(id, wineModelUpdateDTO));
    }

    //DELETE
    @Operation(summary = "Deletes a wine model by its ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                         description = "Wine model deleted successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WineModelResponseDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Wine model not found",
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
    public ResponseEntity<Void> deleteWineModel(@PathVariable Long id) {
        service.deleteWineModel(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
