/**
 * Book class.
 * INTERFACE: implements Borrowable.
 * ENCAPSULATION: private fields + public getters/setters.
 */
public class Book implements Borrowable {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private boolean available;
    private String issuedTo;      // member ID, or "" if the book is in the library

    public Book(String bookId, String title, String author, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
        this.issuedTo = "";
    }

    // ----- Borrowable interface methods -----
    @Override
    public void issueTo(String memberId) throws BookNotAvailableException {
        if (!available) {
            throw new BookNotAvailableException(
                    "Book '" + title + "' is already issued to member " + issuedTo + ".");
        }
        available = false;
        issuedTo = memberId;
    }

    @Override
    public void returnItem() {
        available = true;
        issuedTo = "";
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    // ----- Getters and setters -----
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getIssuedTo() { return issuedTo; }

    public String getStatus() {
        return available ? "Available" : "Issued";
    }

    @Override
    public String toString() {
        return bookId + " - " + title + " by " + author;
    }
}
