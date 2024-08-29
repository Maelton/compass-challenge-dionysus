package maelton.compass.dionysus.api.v1.model.dto.wine;

import io.swagger.v3.oas.annotations.media.Schema;

import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO used to represent wine registries data in responses")
public record WineResponseDTO(
        UUID id,
        Long modelId,
        Double price,
        LocalDateTime insertionDateTime,
        ProductStatus status
) {}