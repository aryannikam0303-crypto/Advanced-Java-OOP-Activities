import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * MULTITHREADING: this class implements Runnable and runs in a
 * background thread. Every few seconds it prepares a notification
 * message and shows it in the status bar of the dashboard.
 *
 * Swing components must only be changed on the Event Dispatch Thread,
 * so SwingUtilities.invokeLater() is used to update the label.
 */
public class ReminderTask implements Runnable {

    private final LibraryService service;
    private final JLabel notificationLabel;
    private final int intervalSeconds;
    private volatile boolean running = true;
    private int messageNumber = 0;

    public ReminderTask(LibraryService service, JLabel notificationLabel, int intervalSeconds) {
        this.service = service;
        this.notificationLabel = notificationLabel;
        this.intervalSeconds = intervalSeconds;
    }

    @Override
    public void run() {
        try {
            while (running) {
                final String message = buildMessage();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        notificationLabel.setText(message);
                    }
                });
                Thread.sleep(intervalSeconds * 1000L);
            }
        } catch (InterruptedException e) {
            // Thread was interrupted while sleeping - time to stop.
        } finally {
            System.out.println("ReminderTask finished.");
        }
    }

    private String buildMessage() {
        int issued = service.getIssuedBooks();
        int available = service.getAvailableBooks();
        String message;
        switch (messageNumber % 3) {
            case 0:
                message = "Reminder: " + issued + " book(s) are currently issued. Please check due returns.";
                break;
            case 1:
                message = "Info: " + available + " book(s) are available for issue.";
                break;
            default:
                message = "Tip: Use 'Search Books' to find a book by ID, title or author.";
                break;
        }
        messageNumber++;
        return message;
    }

    /** Asks the loop to stop. */
    public void stop() {
        running = false;
    }
}
