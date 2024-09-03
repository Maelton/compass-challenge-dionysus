package maelton.compass.dionysus.api.v1.exception.sale;

import java.util.UUID;

public class SaleUUIDNotFoundException extends RuntimeException {
    public SaleUUIDNotFoundException(UUID id) {
        super("Sale ID '" + id + "' not found");
    }
}
