package maelton.compass.dionysus.api.v1.model.dto.sale;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Valid
@Schema(description = "DTO for creating or updating sales")
public record SaleRequestDTO(
        @NotNull(message = "Costumer ID field is mandatory")
        UUID costumerId,

        @NotNull(message = "Product ID field is mandatory")
        UUID productId
) {}
