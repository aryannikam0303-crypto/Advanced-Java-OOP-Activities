/**
 * ABSTRACTION + INHERITANCE (superclass).
 * Person is an abstract class: it cannot be instantiated directly.
 * Subclasses (Member, Librarian) must implement the abstract methods.
 *
 * ENCAPSULATION: all fields are private, accessed via getters/setters.
 */
public abstract class Person {

    private String id;
    private String name;
    private String email;
    private String phone;

    public Person(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // ----- Abstract methods (every subclass must provide its own version) -----
    public abstract String getRole();

    public abstract String getDetails();

    // ----- Getters and setters (encapsulation) -----
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return getRole() + " - " + name + " (" + id + ")";
    }
}
