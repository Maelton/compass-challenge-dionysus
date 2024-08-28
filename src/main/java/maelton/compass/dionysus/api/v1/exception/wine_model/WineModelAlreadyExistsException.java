package maelton.compass.dionysus.api.v1.exception.wine_model;

public class WineModelAlreadyExistsException extends RuntimeException {
    public WineModelAlreadyExistsException() {
        super("Wine model already exists");
    }
}
