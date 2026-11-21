/**
 * User-defined (custom) checked exception.
 * Thrown when someone tries to issue a book that is already issued.
 */
public class BookNotAvailableException extends Exception {

    public BookNotAvailableException(String message) {
        super(message);
    }
}
