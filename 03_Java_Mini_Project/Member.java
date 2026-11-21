/**
 * INHERITANCE: Member is a subclass of Person.
 * POLYMORPHISM: overrides getRole() and getDetails() (method overriding).
 */
public class Member extends Person {

    public static final int MAX_BOOKS = 3;   // a member can hold at most 3 books

    private int booksIssued;

    public Member(String id, String name, String email, String phone) {
        super(id, name, email, phone);       // call the Person constructor
        this.booksIssued = 0;
    }

    public int getBooksIssued() { return booksIssued; }

    public boolean canBorrow() {
        return booksIssued < MAX_BOOKS;
    }

    public void incrementIssued() { booksIssued++; }

    public void decrementIssued() {
        if (booksIssued > 0) {
            booksIssued--;
        }
    }

    @Override
    public String getRole() {
        return "Member";
    }

    @Override
    public String getDetails() {
        return getName() + " | " + getEmail() + " | Phone: " + getPhone()
                + " | Books issued: " + booksIssued + "/" + MAX_BOOKS;
    }
}
