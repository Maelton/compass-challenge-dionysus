package maelton.compass.dionysus.api.v1.exception.wine_model;

public class WineModelIdNotFoundException extends RuntimeException {
    public WineModelIdNotFoundException(Long id) {
        super("Wine model id '" + id + "' not found");
    }
}
