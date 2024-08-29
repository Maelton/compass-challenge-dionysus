package maelton.compass.dionysus.api.v1.exception.wine;

import java.util.UUID;

public class WineUUIDNotFoundException extends RuntimeException {
    public WineUUIDNotFoundException(UUID id) {
        super("Wine registry UUID '" + id + "' not found");
    }
}
