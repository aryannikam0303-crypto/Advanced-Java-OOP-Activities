// Program 6: Multithreading by Extending the Thread Class
class TaskThread extends Thread {
    private String taskName;

    TaskThread(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(taskName + " - Step " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(taskName + " was interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class Program06_MultithreadingThread {
    public static void main(String[] args) {
        TaskThread task1 = new TaskThread("Download Task");
        TaskThread task2 = new TaskThread("Printing Task");

        task1.start();
        task2.start();

        try {
            task1.join();
            task2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Both tasks completed.");
    }
}
