package maelton.compass.dionysus.api.v1.model.dto.wine_model;

import io.swagger.v3.oas.annotations.media.Schema;

import maelton.compass.dionysus.api.v1.model.enums.WineType;

@Schema(description = "DTO used to represent wine models data in responses")
public record WineModelResponseDTO(
        Long id,
        String brand,
        String name,
        double volume,
        double abv,
        WineType type,
        double recommendedPrice
) {}
