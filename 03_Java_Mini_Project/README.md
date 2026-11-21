# Library Management System (Java Swing GUI)

## 1. Introduction
A desktop **Library Management System** built with **Core Java, Swing and AWT** as an Advanced Java OOP project. An admin can log in, manage books and members, and issue/return books. Data is held in memory using the Java Collections Framework (sample data is loaded at start-up).

## 2. Objectives
- Apply the five core OOP concepts in a real application.
- Demonstrate exception handling, multithreading and collections.
- Build a clean, easy-to-use GUI with Swing and AWT event handling.
- Keep code beginner-friendly, well commented and split into separate classes.

## 3. Features
- Admin login (with show-password option)
- Dashboard with cards: Total, Available, Issued books and Registered members
- Add new book (duplicate Book IDs blocked)
- View all books in a `JTable` (status coloured green/red)
- Search by ID, title or author
- Register and view members (auto-generated Member ID)
- Issue book (limit: 3 books per member) and return book; availability updates automatically
- Recent-activity list and a background notification bar
- About, Logout and Exit options
- Input validation with `JOptionPane` error and success messages

## 4. Requirements
**Software:** JDK 8 or later, VS Code with the *Extension Pack for Java*, Windows / Linux / macOS
**Hardware:** 2 GB RAM (4 GB recommended), any dual-core processor, 100 MB free disk space, 1280x720 display or better

## 5. Technologies Used
Core Java, Java Swing (`javax.swing`), AWT (`java.awt`: layouts, colours, events), Java Collections Framework, `Runnable`/`Thread`.

## 6. OOP Concepts and Where They Are Implemented
| Concept | Where |
|---|---|
| **Inheritance** | `Person` (superclass) -> `Member` and `Librarian` (subclasses) |
| **Polymorphism** | Method overriding of `getRole()` and `getDetails()` in `Member` / `Librarian`. Runtime polymorphism in `LibraryService.getPeopleSummary()`, where a `List<Person>` holds both kinds of objects and the correct overridden method runs. |
| **Abstraction** | `Person` is an `abstract` class with abstract methods `getRole()` and `getDetails()` |
| **Interface** | `Borrowable` (`issueTo`, `returnItem`, `isAvailable`) implemented by `Book`. `Refreshable` is implemented by the screens. |
| **Encapsulation** | Private fields with public getters/setters in `Person`, `Member`, `Book` |

## 7. Exception Handling, Multithreading and Collections
- **Exception handling:** `try-catch-finally` in `AddBookPanel`, `IssueBookPanel`, `ReturnBookPanel`, `MemberPanel`, `ReminderTask`. User-defined exception: `BookNotAvailableException` (thrown by `Book.issueTo()` when a book is already issued).
- **Multithreading:** `ReminderTask implements Runnable`, started as a daemon thread in `DashboardFrame`. It refreshes the notification bar every 6 seconds and updates Swing safely with `SwingUtilities.invokeLater()`.
- **Collections:** `ArrayList<Book>` (books), `HashMap<String, Member>` (members by ID), `ArrayList<String>` (activity log), `Collections.sort` with a `Comparator`.

## 8. Project Structure
```
LibraryManagementSystem/
├── Main.java                    Entry point
├── LoginFrame.java              Login screen
├── DashboardFrame.java          Main window, menu, CardLayout, status bar
├── DashboardPanel.java          Summary cards + recent activity
├── AddBookPanel.java            Add New Book screen
├── ViewBooksPanel.java          View All Books (JTable)
├── SearchBooksPanel.java        Search by ID / title / author
├── IssueBookPanel.java          Issue Book screen
├── ReturnBookPanel.java         Return Book screen
├── MemberPanel.java             Member Management screen
├── AboutPanel.java              About screen
├── Person.java                  Abstract superclass
├── Member.java                  Subclass of Person
├── Librarian.java               Subclass of Person (admin)
├── Book.java                    Implements Borrowable
├── Borrowable.java              Interface
├── Refreshable.java             Interface for screens
├── LibraryService.java          Business logic + collections
├── BookNotAvailableException.java   User-defined exception
├── ReminderTask.java            Background thread (Runnable)
├── Theme.java                   Colours, fonts, UI helpers
└── README.md
```

## 9. How to Compile and Run
**Command line**
```
cd LibraryManagementSystem
javac *.java
java Main
```
**VS Code**
1. Install the *Extension Pack for Java*.
2. File -> Open Folder -> select `LibraryManagementSystem`.
3. Open `Main.java` and click **Run** above `main`, or press `F5`.

## 10. Login and Test Cases
**Login:** username `admin`, password `admin123`

| # | Test | Steps | Expected result |
|---|---|---|---|
| 1 | Empty login | Click Login with blank fields | Validation warning |
| 2 | Wrong login | `admin` / `wrong` | "Invalid username or password" |
| 3 | Add book | Add Book: ID `B107`, Title `Clean Code`, Author `Robert Martin` | Success; Total Books becomes 7 |
| 4 | Duplicate ID | Add another book with ID `B107` (or `b107`) | Error: duplicate Book ID |
| 5 | Search | Search Books: Title `java` | Shows B101 and B102 |
| 6 | Search not found | Author `xyz123` | "No books found" message |
| 7 | Register member | Members: Name `Rohan Deshmukh`, email `rohan@college.edu`, phone `9988776655` | Member `M003` created |
| 8 | Invalid phone | Phone `12345` | Validation error (10 digits needed) |
| 9 | Issue book | Issue: Book `B101`, Member `M002` | Success; Available -1, Issued +1 |
| 10 | Issue issued book | Issue: Book `B102` (already issued to M001) | Custom `BookNotAvailableException` message |
| 11 | Unknown member | Issue: Book `B103`, Member `M999` | Error: member not found |
| 12 | Return book | Return: Book `B101` | Success; book Available again |
| 13 | Return not-issued book | Return: Book `B104` | Error: book is not issued |
| 14 | Member limit | Issue 4 books to one member | 4th issue blocked (max 3) |

## 11. Future Scope
- Store data permanently (file I/O or MySQL with JDBC)
- Due dates and fine calculation
- Multiple user roles and encrypted passwords
- Edit/delete books and members, issue history report
- Barcode scanning, email reminders, export to PDF/Excel
