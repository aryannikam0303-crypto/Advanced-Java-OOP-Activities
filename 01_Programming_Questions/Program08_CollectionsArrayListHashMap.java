// Program 8: Collections Framework using ArrayList and HashMap
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program08_CollectionsArrayListHashMap {
    public static void main(String[] args) {
        ArrayList<String> students = new ArrayList<>();
        students.add("Aarav");
        students.add("Isha");
        students.add("Kabir");
        students.add("Meera");

        System.out.println("Student List:");
        for (String student : students) {
            System.out.println(student);
        }

        HashMap<Integer, String> studentRecords = new HashMap<>();
        studentRecords.put(101, "Aarav");
        studentRecords.put(102, "Isha");
        studentRecords.put(103, "Kabir");

        System.out.println("\nStudent Records:");
        for (Map.Entry<Integer, String> entry : studentRecords.entrySet()) {
            System.out.println("Roll No: " + entry.getKey()
                    + ", Name: " + entry.getValue());
        }

        int searchRollNo = 102;
        if (studentRecords.containsKey(searchRollNo)) {
            System.out.println("\nStudent found: "
                    + studentRecords.get(searchRollNo));
        } else {
            System.out.println("\nStudent not found.");
        }
    }
}
