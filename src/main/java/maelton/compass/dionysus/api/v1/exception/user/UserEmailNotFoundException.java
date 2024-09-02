package maelton.compass.dionysus.api.v1.exception.user;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String email) {
        super("User with email '" + email + "' was not found");
    }
}
