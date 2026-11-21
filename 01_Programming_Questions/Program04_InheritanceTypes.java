// Program 4: Demonstration of Multilevel and Hierarchical Inheritance
class Person {
    void showPersonDetails() {
        System.out.println("This is the Person class.");
    }
}

// Multilevel inheritance: Person -> Student -> GraduateStudent
class Student extends Person {
    void study() {
        System.out.println("Student is studying.");
    }
}

class GraduateStudent extends Student {
    void research() {
        System.out.println("Graduate student is doing research.");
    }
}

// Hierarchical inheritance: Employee and Customer both extend Person
class EmployeeRole extends Person {
    void work() {
        System.out.println("Person works as an employee.");
    }
}

class Customer extends Person {
    void purchase() {
        System.out.println("Person is purchasing a product.");
    }
}

public class Program04_InheritanceTypes {
    public static void main(String[] args) {
        System.out.println("MULTILEVEL INHERITANCE");
        GraduateStudent graduate = new GraduateStudent();
        graduate.showPersonDetails();
        graduate.study();
        graduate.research();

        System.out.println("\nHIERARCHICAL INHERITANCE");
        EmployeeRole employee = new EmployeeRole();
        employee.showPersonDetails();
        employee.work();

        Customer customer = new Customer();
        customer.showPersonDetails();
        customer.purchase();
    }
}
