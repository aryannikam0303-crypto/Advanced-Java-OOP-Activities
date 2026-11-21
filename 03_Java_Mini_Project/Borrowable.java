/**
 * INTERFACE: defines the behaviour of anything that can be borrowed.
 * The Book class implements this interface.
 */
public interface Borrowable {

    /** Issue the item to a member. Throws if it is already issued. */
    void issueTo(String memberId) throws BookNotAvailableException;

    /** Return the item to the library. */
    void returnItem();

    /** @return true if the item can be issued right now. */
    boolean isAvailable();
}
