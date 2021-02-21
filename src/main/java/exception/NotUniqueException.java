package exception;

public class NotUniqueException  extends Exception {

    private static final long serialVersionUID = 9211924848964510763L;

    public NotUniqueException(String message) {
        super(message);
    }
}
