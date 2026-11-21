# 10 Advanced-Level Non-Programming Questions – Java OOP

**Subject:** Object Oriented Programming Using Java  
**Level:** Advanced  
**Section:** Non-Programming Questions

---

## Q1. Differentiate abstraction and encapsulation. Explain how they work together in a real-world application.

**Answer:**

**Abstraction** means showing only essential features to the user and hiding implementation details. In Java, abstraction is achieved using abstract classes and interfaces.

**Encapsulation** means combining data and methods into a single class and restricting direct access to data using access modifiers such as `private`. Public methods such as getters, setters, and validated operations provide controlled access.

| Abstraction | Encapsulation |
|---|---|
| Hides implementation complexity. | Protects an object's internal data. |
| Focuses on what an object does. | Focuses on how data is accessed and modified. |
| Achieved using abstract classes and interfaces. | Achieved using classes, access modifiers, and methods. |

**Example – ATM:** A customer uses options such as withdraw and check balance without knowing the internal banking process (abstraction). The account balance is private and can be changed only through validated deposit or withdrawal methods (encapsulation).

They work together because abstraction provides a simple public interface, while encapsulation protects the data and implementation behind that interface.

---

## Q2. Why does Java not support multiple inheritance through classes? How do interfaces solve this limitation?

**Answer:**

Multiple inheritance through classes means that one class inherits from two or more parent classes. Java does not allow this because it can create ambiguity, especially when both parent classes contain a method with the same signature. This is commonly explained by the **diamond problem**.

For example, if classes `Father` and `Mother` both define `show()` and class `Child` inherits from both, the compiler would need a clear rule to decide which implementation should be used.

Java supports multiple inheritance of type through interfaces:

```java
interface Printable {
    void print();
}

interface Scannable {
    void scan();
}

class MultiFunctionMachine implements Printable, Scannable {
    public void print() {
        System.out.println("Printing document");
    }

    public void scan() {
        System.out.println("Scanning document");
    }
}
```

A class can implement multiple interfaces and provide implementations for their abstract methods. If interfaces contain conflicting default methods, the implementing class must resolve the conflict explicitly.

Thus, interfaces provide flexibility while avoiding the ambiguity associated with inheriting implementation from multiple classes.

---

## Q3. Compare method overloading and method overriding. Explain their relationship with compile-time and runtime polymorphism.

**Answer:**

**Method overloading** occurs when multiple methods in the same class have the same name but different parameter lists. The compiler determines which method to call based on the arguments. It is an example of compile-time polymorphism.

**Method overriding** occurs when a subclass provides its own implementation of an inherited instance method with a compatible signature. The method selected at runtime depends on the actual object. It is an example of runtime polymorphism.

| Method Overloading | Method Overriding |
|---|---|
| Same method name with different parameters. | Subclass redefines an inherited method. |
| Usually occurs in the same class. | Requires inheritance. |
| Resolved at compile time. | Resolved at runtime for instance methods. |
| Return type alone cannot distinguish overloads. | Return type must be same or covariant. |
| Inheritance is not required. | Inheritance is required. |

**Example:**

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
}

class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

The two `add()` methods demonstrate overloading. The `Dog` class's `sound()` method demonstrates overriding.

---

## Q4. What is dynamic method dispatch? Explain how it supports runtime polymorphism in Java.

**Answer:**

Dynamic method dispatch is the mechanism by which a call to an overridden instance method is resolved at runtime rather than being decided only from the reference type at compile time.

A superclass reference can refer to an object of a subclass. When an overridden method is called through that reference, Java executes the implementation belonging to the actual object.

```java
class Vehicle {
    void start() {
        System.out.println("Vehicle starts");
    }
}

class Car extends Vehicle {
    @Override
    void start() {
        System.out.println("Car starts with a key");
    }
}

class Bike extends Vehicle {
    @Override
    void start() {
        System.out.println("Bike starts with a button");
    }
}

public class DispatchDemo {
    public static void main(String[] args) {
        Vehicle v;

        v = new Car();
        v.start();

        v = new Bike();
        v.start();
    }
}
```

**Output:**

```text
Car starts with a key
Bike starts with a button
```

The reference type is `Vehicle`, but the executed method depends on whether the object is a `Car` or `Bike`. This makes programs extensible because new subclasses can provide their own behavior without changing the calling code.

---

## Q5. Compare abstract classes and interfaces in Java. When should a developer choose each?

**Answer:**

An **abstract class** is a class that cannot be instantiated directly. It can contain abstract methods, concrete methods, constructors, instance variables, and state.

An **interface** defines a contract that implementing classes agree to follow. It can contain abstract methods, constants, default methods, static methods, and private helper methods.

| Abstract Class | Interface |
|---|---|
| Declared using `abstract class`. | Declared using `interface`. |
| A class extends one class only. | A class can implement multiple interfaces. |
| Can have instance fields and constructors. | Cannot have ordinary instance constructors or per-object instance fields. |
| Can contain abstract and concrete methods. | Can contain abstract, default, static, and private methods. |
| Useful for closely related classes sharing state or common implementation. | Useful for defining common capabilities across possibly unrelated classes. |

**When to choose:**

- Choose an abstract class when related subclasses need common state, constructors, or shared implementation.
- Choose an interface when different classes need to follow the same capability or contract.
- A design can use both when a shared base implementation and multiple capabilities are required.

---

## Q6. Explain the purpose of `this`, `super`, and `final` in Java with suitable examples.

**Answer:**

**1. `this` keyword:** Refers to the current object. It is commonly used to distinguish instance variables from parameters with the same name or to call another constructor in the same class.

**2. `super` keyword:** Refers to the immediate parent-class part of the current object. It can call a parent constructor, access a parent field, or invoke an overridden parent method.

**3. `final` keyword:** Restricts modification or extension depending on where it is used:
- A final variable can be assigned only once.
- A final method cannot be overridden.
- A final class cannot be extended.

```java
class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    void display() {
        System.out.println("Name: " + name);
    }
}

class Student extends Person {
    final int rollNumber;

    Student(String name, int rollNumber) {
        super(name);
        this.rollNumber = rollNumber;
    }

    @Override
    void display() {
        super.display();
        System.out.println("Roll Number: " + this.rollNumber);
    }
}
```

These keywords improve clarity, support inheritance, and help protect values and behavior from unintended changes.

---

## Q7. Differentiate checked and unchecked exceptions. Explain how a developer should handle each.

**Answer:**

An exception is an event that interrupts the normal flow of program execution.

**Checked exceptions** are checked by the compiler. A method must catch them or declare them using `throws`. Examples include `IOException` and `SQLException`.

**Unchecked exceptions** are subclasses of `RuntimeException`. The compiler does not require them to be caught or declared. Examples include `NullPointerException`, `ArithmeticException`, and `IllegalArgumentException`.

| Checked Exceptions | Unchecked Exceptions |
|---|---|
| Checked at compile time. | Not required to be handled at compile time. |
| Must be caught or declared. | Catching or declaring is optional. |
| Often represent conditions an application may reasonably recover from. | Often indicate programming errors or invalid arguments. |
| Example: `IOException`. | Example: `NullPointerException`. |

**Good handling practices:**

- Catch an exception where the program can take a meaningful recovery action.
- Use `try-with-resources` for resources such as files.
- Validate input to prevent avoidable runtime exceptions.
- Do not silently ignore exceptions.
- Create custom exceptions when a domain-specific error needs to be represented.

---

## Q8. Explain the Java thread lifecycle and the importance of synchronization.

**Answer:**

A thread is a lightweight unit of execution within a process. Java manages thread states through the `Thread.State` enumeration.

Important thread states are:

1. **NEW:** A thread object has been created but `start()` has not been called.
2. **RUNNABLE:** The thread is eligible to run or is running under the JVM scheduler.
3. **BLOCKED:** The thread is waiting to acquire a monitor lock.
4. **WAITING:** The thread is waiting indefinitely for another thread to perform an action.
5. **TIMED_WAITING:** The thread waits for a specified time, for example through `sleep()`.
6. **TERMINATED:** The thread has completed execution.

**Synchronization** controls access to shared resources. If multiple threads update shared data at the same time without coordination, a race condition can occur and produce incorrect results.

A synchronized method or block uses an object's monitor lock so that only one thread at a time can execute the protected section using that same lock.

Synchronization helps maintain data consistency, but excessive locking can reduce performance and poorly designed locking can cause deadlocks.

---

## Q9. Differentiate `Comparable` and `Comparator` in Java. When is each useful?

**Answer:**

Both `Comparable` and `Comparator` are used to define ordering among objects.

**Comparable:**
- Belongs to the `java.lang` package.
- The class implements `Comparable<T>`.
- Defines the natural ordering through `compareTo()`.
- Usually provides one default ordering for the class.

**Comparator:**
- Belongs to the `java.util` package.
- Defines an external comparison strategy through `compare()`.
- Allows multiple different orderings without changing the original class.

| Comparable | Comparator |
|---|---|
| Uses `compareTo()`. | Uses `compare()`. |
| Implemented by the class being compared. | Usually defined separately or with a lambda. |
| Defines natural ordering. | Defines custom ordering. |
| Often suitable for a primary/default sort order. | Suitable for sorting by different fields or criteria. |

**Example:**

```java
import java.util.*;

class Student implements Comparable<Student> {
    int rollNo;
    String name;

    Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    public int compareTo(Student other) {
        return Integer.compare(this.rollNo, other.rollNo);
    }
}

class ComparatorDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(3, "Riya"));
        students.add(new Student(1, "Aman"));
        students.add(new Student(2, "Neha"));

        Collections.sort(students); // Sort by roll number
        students.sort(Comparator.comparing(s -> s.name)); // Sort by name
    }
}
```

Use `Comparable` for a natural order and `Comparator` when multiple sorting rules are needed.

---

## Q10. How do OOP principles improve software maintainability, reusability, and extensibility? Explain with an application example.

**Answer:**

Object-Oriented Programming organizes software around objects that combine state and behavior. Its major principles help developers manage complex applications.

**1. Encapsulation:** Keeps data protected and allows controlled access. A change to internal data representation can be made without requiring every part of the application to change.

**2. Abstraction:** Hides unnecessary implementation details and exposes clear operations. This reduces complexity for users of a class.

**3. Inheritance:** Allows a subclass to reuse and specialize behavior from a parent class. It can reduce duplication when there is a genuine “is-a” relationship.

**4. Polymorphism:** Allows code to work with a common parent type or interface while different objects provide their own behavior. This makes it easier to add new implementations.

**Example – Library Management System:**

A library application may define a common `LibraryUser` type and specialized student and faculty users. A `Book` class can protect its availability status through private fields and controlled methods. An interface such as `Borrowable` can define borrowing operations.

When a new user type is introduced, the application can add a new implementation while keeping much of the existing code unchanged.

Therefore, OOP supports maintainability through modular design, reusability through shared behavior, and extensibility through interfaces and polymorphism. Good class design is still important; inheritance should not be used when composition is a better fit.
