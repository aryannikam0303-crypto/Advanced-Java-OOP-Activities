// Program 10: Banking Management System using OOP Concepts
import java.util.Scanner;

class Account {
    private final String accountHolder;
    private double balance;

    Account(String accountHolder, double openingBalance) {
        this.accountHolder = accountHolder;
        this.balance = openingBalance;
    }

    String getAccountHolder() {
        return accountHolder;
    }

    double getBalance() {
        return balance;
    }

    void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        System.out.println("Deposit successful.");
    }

    void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful.");
        }
    }
}

public class Program10_BankingManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = new Account("Aryan", 10000);
        int choice;

        do {
            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Display Account Details");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Account Holder: " + account.getAccountHolder());
                    System.out.println("Current Balance: Rs. " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    if (scanner.hasNextDouble()) {
                        account.deposit(scanner.nextDouble());
                    } else {
                        System.out.println("Invalid amount.");
                        scanner.next();
                    }
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    if (scanner.hasNextDouble()) {
                        account.withdraw(scanner.nextDouble());
                    } else {
                        System.out.println("Invalid amount.");
                        scanner.next();
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using the banking system.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
