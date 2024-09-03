package maelton.compass.dionysus.api.v1.model.dto.sale;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO used to represent sales data in responses")
public record SaleResponseDTO(
        UUID id,
        UUID costumerId,
        String costumerName,
        String costumerEmail,
        UUID productId,
        String productName,
        double totalSaleValue
) {}
