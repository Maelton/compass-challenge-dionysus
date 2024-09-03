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
import maelton.compass.dionysus.api.v1.model.dto.sale.SaleRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.sale.SaleResponseDTO;
import maelton.compass.dionysus.api.v1.service.SaleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
@Tag(name = "Sales Management", description = "Endpoint for managing sales")
@SecurityRequirement(name = "jwtAuthentication")
public class SaleController {
    
    private final SaleService service;
    public SaleController(SaleService service) {
        this.service = service;
    }
    
    //CREATE
    @Operation(summary = "Creates a new sale", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "New sale created successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponseDTO.class)
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
                         description = "Specified costumer or selling product not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "409",
                         description = "Product not available for sale",
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
    public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleRequestDTO saleCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSale(saleCreateDTO));
    }
    
    //READ ALL
    @Operation(summary = "Retrieves all sales", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "All sales returned",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = SaleResponseDTO.class)
                            )
                         }
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SaleResponseDTO>> getAllSales() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllSales());
    }
    
    //READ BY ID
    @Operation(summary = "Retrieves a sale by its ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Sale returned successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponseDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Sale UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                         }
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getSaleById(id));
    }
    
    //UPDATE
    @Operation(summary = "Updates a sale by its ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Sale updated successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponseDTO.class)
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
                         description = "Sale UUID, Costumer UUID or Product UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "409",
                         description = "Product is not available for sale",
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
    public ResponseEntity<SaleResponseDTO> updateSale(@PathVariable UUID id, @Valid @RequestBody SaleRequestDTO saleUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateSale(id, saleUpdateDTO));
    }
    
    //DELETE
    @Operation(summary = "Deletes a sale by its ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                         description = "Sale deleted successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponseDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "404",
                         description = "Sale UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                         }
            )
        }
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteSale(UUID id) {
        service.deleteSale(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
