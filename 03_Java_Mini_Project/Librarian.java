/**
 * Second subclass of Person - represents the admin / librarian who logs in.
 * Having two subclasses lets us show RUNTIME POLYMORPHISM:
 * a Person reference can point to a Member or a Librarian, and the
 * correct overridden method is chosen while the program is running.
 */
public class Librarian extends Person {

    private String username;
    private String password;

    public Librarian(String id, String name, String email, String phone,
                     String username, String password) {
        super(id, name, email, phone);
        this.username = username;
        this.password = password;
    }

    /** Checks the login credentials. */
    public boolean authenticate(String user, String pass) {
        return username.equals(user) && password.equals(pass);
    }

    @Override
    public String getRole() {
        return "Librarian (Admin)";
    }

    @Override
    public String getDetails() {
        return getName() + " | " + getEmail() + " | Username: " + username;
    }
}
