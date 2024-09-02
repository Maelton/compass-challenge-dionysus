package maelton.compass.dionysus.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import maelton.compass.dionysus.api.v1.exception.handler.ExceptionResponse;
import maelton.compass.dionysus.api.v1.model.dto.user.UserRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserResponseDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserResponseDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserResponseDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserResponseDTO;
import maelton.compass.dionysus.api.v1.service.UserService;

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
@RequestMapping("/users")
@Tag(name = "Users Management", description = "Endpoint for managing users")
//@SecurityRequirement(name = "jwtAuthentication")
public class UserController {

    public final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }
    
    //CREATE
    @Operation(summary = "Creates a new user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "New user created successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                    description = "Email address already exists",
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
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userCreateDTO));
    }
    
    //READ ALL
    @Operation(summary = "Retrieves all users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "All users returned",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = UserResponseDTO.class)
                            )
                         }
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers());
    }
    
    //READ BY ID
    @Operation(summary = "Retrieves a user by its ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "User returned successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "404",
                         description = "User UUID not found",
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
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
    }
    
    //UPDATE
    @Operation(summary = "Updates a user by its ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "User updated successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
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
                         description = "User UUID not found",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "409",
                         description = "Email address already exists",
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
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserRequestDTO userUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updatedUser(id, userUpdateDTO));
    }
    
    //DELETE
    @Operation(summary = "Deletes a user by its ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                         description = "User deleted successfully",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "404",
                         description = "User UUID not found",
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
    public ResponseEntity<Void> deleteUser(UUID id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
