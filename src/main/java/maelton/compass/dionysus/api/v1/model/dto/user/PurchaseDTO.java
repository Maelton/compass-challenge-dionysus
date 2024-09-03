package maelton.compass.dionysus.api.v1.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO used to represent a user purchase")
public record PurchaseDTO(
        UUID saleId,
        LocalDateTime saleDate,
        String productBrand,
        String productName,
        double productPrice,
        double totalSaleValue
) {}
