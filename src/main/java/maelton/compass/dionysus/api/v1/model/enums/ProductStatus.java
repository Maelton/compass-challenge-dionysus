package maelton.compass.dionysus.api.v1.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    AVAILABLE("In Stock"),
    UNAVAILABLE("On Hold"),
    SOLD("Sold");

    private final String status;
}
