package maelton.compass.dionysus.api.v1.exception.wine;

import maelton.compass.dionysus.api.v1.model.entity.Wine;

public class WineNotAvailableException extends RuntimeException {
    public WineNotAvailableException(Wine wine) {
        super("Wine ID '" + wine.getId() + "' status is: '" + wine.getStatus() + "'. It is not available for sale.");
    }
}
