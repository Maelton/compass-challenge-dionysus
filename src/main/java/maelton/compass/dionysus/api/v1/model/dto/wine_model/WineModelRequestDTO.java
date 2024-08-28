package maelton.compass.dionysus.api.v1.model.dto.wine_model;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import maelton.compass.dionysus.api.v1.model.enums.WineType;

@Valid
@Schema(description = "DTO for creating or updating wine models")
public record WineModelRequestDTO(
        @NotNull(message = "field brand is mandatory")
        @NotBlank(message = "field brand is mandatory")
        String brand,

        @NotNull(message = "field name is mandatory")
        @NotBlank(message = "field name is mandatory")
        String name,

        @NotNull(message = "field volume is mandatory")
        @DecimalMin(value = "0.25", message = "Volume cannot be less than 0.25 liters")
        double volume,

        @NotNull(message = "field volume is mandatory")
        @DecimalMin(value = "0.0", message = "alcohol by volume invalid")
        @DecimalMax(value = "99.00", message = "alcohol by volume must not be greater than 99%")
        double abv,

        @NotNull(message = "field volume is mandatory")
        WineType type,

        @DecimalMin(value = "0.0", message = "field recommendedPrice cannot be a negative value")
        double recommendedPrice
) {}
