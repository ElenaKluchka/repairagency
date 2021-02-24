package exception;

/**
 * Exception for saving user with unique parameters which already exists in data
 * base for another user.
 * 
 * @author Kliuchka Olena.
 *
 */
public class NotUniqueException extends Exception {

    private static final long serialVersionUID = 9211924848964510763L;

    public NotUniqueException(String message) {
        super(message);
    }
}
