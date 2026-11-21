// Program 7: Multithreading using the Runnable Interface
class NumberPrinter implements Runnable {
    private int start;
    private int end;

    NumberPrinter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int number = start; number <= end; number++) {
            System.out.println(Thread.currentThread().getName() + ": " + number);
        }
    }
}

public class Program07_RunnableInterface {
    public static void main(String[] args) {
        NumberPrinter firstTask = new NumberPrinter(1, 5);
        NumberPrinter secondTask = new NumberPrinter(6, 10);

        Thread thread1 = new Thread(firstTask, "Thread-A");
        Thread thread2 = new Thread(secondTask, "Thread-B");

        thread1.start();
        thread2.start();
    }
}
