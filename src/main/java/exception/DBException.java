package exception;

public class DBException extends Exception {

    private static final long serialVersionUID = 9211924848964510763L;

    public DBException(String message) {
        super(message);
    }
}
