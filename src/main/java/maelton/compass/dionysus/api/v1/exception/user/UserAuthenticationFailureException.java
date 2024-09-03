package maelton.compass.dionysus.api.v1.exception.user;

public class UserAuthenticationFailureException extends RuntimeException {
    public UserAuthenticationFailureException(String message) {
        super(message);
    }
}