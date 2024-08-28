package maelton.compass.dionysus.api.v1.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineType {
    RED("Red Wine"),
    WHITE("White Wine"),
    ROSE("Rosé Wine");

    private final String name;
}
