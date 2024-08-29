package maelton.compass.dionysus.api.v1.model.dto.wine;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;

@Valid
@Schema(description = "DTO for creating or updating wine registries")
public record WineRequestDTO(
        @NotNull(message = "Model ID field is mandatory")
        Long modelId,

        @DecimalMin(value = "0.0", message = "Price field cannot be a negative value")
        @NotNull(message = "Price field is mandatory")
        Double price,

        @NotNull(message = "Status field is mandatory")
        ProductStatus status
) {}