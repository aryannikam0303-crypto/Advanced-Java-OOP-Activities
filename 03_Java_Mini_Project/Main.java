import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point of the Library Management System.
 * Starts the application on the Swing Event Dispatch Thread.
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Cross-platform look and feel so our green colours show on every OS
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set look and feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LibraryService service = new LibraryService();
                new LoginFrame(service).setVisible(true);
            }
        });
    }
}
