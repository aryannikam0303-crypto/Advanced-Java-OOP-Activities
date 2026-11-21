// Program 2: Abstract Class and Interface
interface Payable {
    double calculatePayment();
}

abstract class Shape implements Payable {
    abstract double calculateArea();
}

class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePayment() {
        return calculateArea() * 2;
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePayment() {
        return calculateArea() * 2;
    }
}

public class Program02_AbstractClassInterface {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);

        System.out.printf("Circle Area: %.2f%n", circle.calculateArea());
        System.out.printf("Circle Payment: %.2f%n", circle.calculatePayment());

        System.out.printf("Rectangle Area: %.2f%n", rectangle.calculateArea());
        System.out.printf("Rectangle Payment: %.2f%n", rectangle.calculatePayment());
    }
}
