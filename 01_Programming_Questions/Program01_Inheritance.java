// Program 1: Inheritance and Method Overriding
class Employee {
    protected String name;
    protected double basicSalary;

    Employee(String name, double basicSalary) {
        this.name = name;
        this.basicSalary = basicSalary;
    }

    double calculateSalary() {
        return basicSalary;
    }
}

class Manager extends Employee {
    private double allowance;

    Manager(String name, double basicSalary, double allowance) {
        super(name, basicSalary);
        this.allowance = allowance;
    }

    @Override
    double calculateSalary() {
        return basicSalary + allowance;
    }
}

class Developer extends Employee {
    private double projectBonus;

    Developer(String name, double basicSalary, double projectBonus) {
        super(name, basicSalary);
        this.projectBonus = projectBonus;
    }

    @Override
    double calculateSalary() {
        return basicSalary + projectBonus;
    }
}

public class Program01_Inheritance {
    public static void main(String[] args) {
        Manager manager = new Manager("Amit", 50000, 15000);
        Developer developer = new Developer("Neha", 40000, 10000);

        System.out.println("Manager: " + manager.name);
        System.out.println("Total Salary: Rs. " + manager.calculateSalary());

        System.out.println("\nDeveloper: " + developer.name);
        System.out.println("Total Salary: Rs. " + developer.calculateSalary());
    }
}
