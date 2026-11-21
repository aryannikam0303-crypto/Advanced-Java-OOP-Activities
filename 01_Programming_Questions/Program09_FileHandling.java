// Program 9: File Handling with Exception Handling
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Program09_FileHandling {
    public static void main(String[] args) {
        String fileName = "student_details.txt";

        // Write data to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Student Name: Aryan");
            writer.newLine();
            writer.write("Course: B.Tech Computer Science");
            writer.newLine();
            writer.write("Subject: Java OOP");
            System.out.println("Data written successfully.");
        } catch (IOException e) {
            System.out.println("Error while writing file: " + e.getMessage());
        }

        // Read data from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            System.out.println("\nReading file contents:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
    }
}
