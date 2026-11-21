import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Business-logic class. All GUI screens talk to this class.
 *
 * COLLECTIONS FRAMEWORK:
 *   - ArrayList<Book>        : stores all books
 *   - HashMap<String,Member> : stores members, key = member ID
 *   - ArrayList<String>      : recent activity log
 *
 * Methods are synchronized because the ReminderTask thread also reads data.
 */
public class LibraryService {

    private final List<Book> books = new ArrayList<>();
    private final Map<String, Member> members = new HashMap<>();
    private final List<String> activityLog = new ArrayList<>();
    private final Librarian admin;

    public LibraryService() {
        admin = new Librarian("L001", "Library Admin", "admin@college.edu",
                "9000000000", "admin", "admin123");
        loadSampleData();
    }

    // ---------------------------------------------------------------
    // Sample data so the application is not empty on first run
    // ---------------------------------------------------------------
    private void loadSampleData() {
        addBook("B101", "Core Java Volume I", "Cay Horstmann", "Programming");
        addBook("B102", "Head First Java", "Kathy Sierra", "Programming");
        addBook("B103", "Data Structures Using C", "Reema Thareja", "Technology");
        addBook("B104", "Wings of Fire", "A. P. J. Abdul Kalam", "Biography");
        addBook("B105", "The Discovery of India", "Jawaharlal Nehru", "History");
        addBook("B106", "A Brief History of Time", "Stephen Hawking", "Science");

        addMember("M001", "Aarav Patil", "aarav@college.edu", "9876543210");
        addMember("M002", "Sneha Kulkarni", "sneha@college.edu", "9123456780");

        try {
            issueBook("B102", "M001");      // one book already issued for demo
        } catch (BookNotAvailableException e) {
            System.out.println("Sample data error: " + e.getMessage());
        }
        activityLog.clear();
        activityLog.add("System started with sample data.");
    }

    // ---------------------------------------------------------------
    // Login
    // ---------------------------------------------------------------
    public boolean login(String username, String password) {
        return admin.authenticate(username, password);
    }

    public Librarian getAdmin() {
        return admin;
    }

    // ---------------------------------------------------------------
    // Book operations
    // ---------------------------------------------------------------
    public synchronized void addBook(String id, String title, String author, String category) {
        if (findBookById(id) != null) {
            throw new IllegalArgumentException("Book ID '" + id + "' already exists. Duplicate IDs are not allowed.");
        }
        books.add(new Book(id, title, author, category));
        log("Book added: " + id + " - " + title);
    }

    public synchronized Book findBookById(String id) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

    public synchronized List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /** field is "ID", "Title" or "Author". Partial, case-insensitive match. */
    public synchronized List<Book> searchBooks(String keyword, String field) {
        List<Book> result = new ArrayList<>();
        String key = keyword.toLowerCase();
        for (Book b : books) {
            String value;
            if (field.equals("ID")) {
                value = b.getBookId();
            } else if (field.equals("Title")) {
                value = b.getTitle();
            } else {
                value = b.getAuthor();
            }
            if (value.toLowerCase().contains(key)) {
                result.add(b);
            }
        }
        return result;
    }

    public synchronized List<Book> getAvailableBookList() {
        List<Book> list = new ArrayList<>();
        for (Book b : books) {
            if (b.isAvailable()) {
                list.add(b);
            }
        }
        return list;
    }

    public synchronized List<Book> getIssuedBookList() {
        List<Book> list = new ArrayList<>();
        for (Book b : books) {
            if (!b.isAvailable()) {
                list.add(b);
            }
        }
        return list;
    }

    // ---------------------------------------------------------------
    // Member operations
    // ---------------------------------------------------------------
    public synchronized String generateMemberId() {
        int n = members.size() + 1;
        String id = String.format("M%03d", n);
        while (members.containsKey(id)) {
            n++;
            id = String.format("M%03d", n);
        }
        return id;
    }

    public synchronized void addMember(String id, String name, String email, String phone) {
        if (members.containsKey(id.toUpperCase())) {
            throw new IllegalArgumentException("Member ID '" + id + "' already exists.");
        }
        members.put(id.toUpperCase(), new Member(id.toUpperCase(), name, email, phone));
        log("Member registered: " + id.toUpperCase() + " - " + name);
    }

    public synchronized Member findMemberById(String id) {
        return members.get(id.toUpperCase());
    }

    /** Returns members sorted by ID (HashMap itself has no fixed order). */
    public synchronized List<Member> getAllMembers() {
        List<Member> list = new ArrayList<>(members.values());
        Collections.sort(list, new Comparator<Member>() {
            @Override
            public int compare(Member a, Member b) {
                return a.getId().compareTo(b.getId());
            }
        });
        return list;
    }

    // ---------------------------------------------------------------
    // Issue and return
    // ---------------------------------------------------------------
    public synchronized void issueBook(String bookId, String memberId) throws BookNotAvailableException {
        Book book = findBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID '" + bookId + "'.");
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("No member found with ID '" + memberId + "'. Please register first.");
        }
        if (!member.canBorrow()) {
            throw new IllegalStateException(member.getName() + " has already reached the limit of "
                    + Member.MAX_BOOKS + " books.");
        }
        book.issueTo(member.getId());        // may throw BookNotAvailableException
        member.incrementIssued();
        log("Issued: " + book.getBookId() + " to " + member.getId() + " (" + member.getName() + ")");
    }

    public synchronized void returnBook(String bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID '" + bookId + "'.");
        }
        if (book.isAvailable()) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is not issued, so it cannot be returned.");
        }
        Member member = findMemberById(book.getIssuedTo());
        if (member != null) {
            member.decrementIssued();
        }
        String memberId = book.getIssuedTo();
        book.returnItem();
        log("Returned: " + book.getBookId() + " from " + memberId);
    }

    // ---------------------------------------------------------------
    // Statistics for dashboard cards
    // ---------------------------------------------------------------
    public synchronized int getTotalBooks() { return books.size(); }

    public synchronized int getAvailableBooks() { return getAvailableBookList().size(); }

    public synchronized int getIssuedBooks() { return getIssuedBookList().size(); }

    public synchronized int getTotalMembers() { return members.size(); }

    // ---------------------------------------------------------------
    // Activity log
    // ---------------------------------------------------------------
    private void log(String message) {
        activityLog.add(message);
    }

    /** Newest activity first. */
    public synchronized List<String> getRecentActivity() {
        List<String> copy = new ArrayList<>(activityLog);
        Collections.reverse(copy);
        return copy;
    }

    // ---------------------------------------------------------------
    // RUNTIME POLYMORPHISM demo
    // A list of Person references holds Librarian and Member objects.
    // getRole() and getDetails() run the version of the real object.
    // ---------------------------------------------------------------
    public synchronized String getPeopleSummary() {
        List<Person> people = new ArrayList<>();
        people.add(admin);
        people.addAll(getAllMembers());

        StringBuilder sb = new StringBuilder();
        for (Person p : people) {
            sb.append(p.getRole()).append(": ").append(p.getDetails()).append("\n");
        }
        return sb.toString();
    }
}
