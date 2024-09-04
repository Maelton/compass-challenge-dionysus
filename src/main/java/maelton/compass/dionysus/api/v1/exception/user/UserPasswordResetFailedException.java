package maelton.compass.dionysus.api.v1.exception.user;

public class UserPasswordResetFailedException extends RuntimeException {
    public UserPasswordResetFailedException(String message) {
        super(message);
    }
}
