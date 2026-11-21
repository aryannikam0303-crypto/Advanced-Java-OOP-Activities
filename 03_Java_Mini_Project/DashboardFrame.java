import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Main window after login.
 * Left: navigation menu. Centre: screens shown with CardLayout.
 * Bottom: status bar where the ReminderTask thread shows notifications.
 */
public class DashboardFrame extends JFrame {

    private final LibraryService service;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final Map<String, JPanel> pages = new LinkedHashMap<>();
    private final Map<String, JButton> menuButtons = new LinkedHashMap<>();
    private final JLabel pageTitle = new JLabel();
    private final JLabel notificationLabel = new JLabel("Starting notifications...");

    private ReminderTask reminderTask;
    private Thread reminderThread;

    public DashboardFrame(LibraryService service) {
        this.service = service;

        setTitle("Library Management System");
        setSize(1100, 680);
        setMinimumSize(new Dimension(980, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(createMainArea(), BorderLayout.CENTER);

        registerPages();
        showPage("Dashboard");
        startReminderThread();
    }

    // ---------------- Sidebar ----------------
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Theme.DARK_GREEN);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 12, 20, 12));

        JLabel logo = new JLabel("LIBRARY");
        logo.setFont(Theme.TITLE_FONT);
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(LEFT_ALIGNMENT);
        JLabel sub = new JLabel("Management System");
        sub.setFont(Theme.NORMAL_FONT);
        sub.setForeground(Theme.LIGHT_GREEN);
        sub.setAlignmentX(LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(sub);
        sidebar.add(Box.createRigidArea(new Dimension(0, 25)));

        String[] names = {"Dashboard", "Add Book", "View All Books", "Search Books",
                "Issue Book", "Return Book", "Members", "About"};
        for (final String name : names) {
            JButton b = createMenuButton(name);
            b.addActionListener(e -> showPage(name));
            menuButtons.put(name, b);
            sidebar.add(b);
            sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logout = createMenuButton("Logout");
        logout.addActionListener(e -> logout());
        JButton exit = createMenuButton("Exit");
        exit.addActionListener(e -> confirmExit());
        sidebar.add(logout);
        sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
        sidebar.add(exit);
        return sidebar;
    }

    private JButton createMenuButton(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.BOLD_FONT);
        b.setBackground(Theme.DARK_GREEN);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return b;
    }

    // ---------------- Main area (header + pages + status bar) ----------------
    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Theme.LIGHT_GREY);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_GREY),
                BorderFactory.createEmptyBorder(14, 25, 14, 25)));
        pageTitle.setFont(Theme.TITLE_FONT);
        pageTitle.setForeground(Theme.DARK_GREEN);
        JLabel user = new JLabel("Logged in as: Admin");
        user.setFont(Theme.NORMAL_FONT);
        user.setForeground(Theme.TEXT_GREY);
        header.add(pageTitle, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);

        // Status bar
        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.setBackground(Theme.LIGHT_GREEN);
        notificationLabel.setFont(Theme.NORMAL_FONT);
        notificationLabel.setForeground(Theme.DARK_GREEN);
        status.add(notificationLabel);

        main.add(header, BorderLayout.NORTH);
        main.add(contentPanel, BorderLayout.CENTER);
        main.add(status, BorderLayout.SOUTH);
        return main;
    }

    private void registerPages() {
        addPage("Dashboard", new DashboardPanel(service));
        addPage("Add Book", new AddBookPanel(service));
        addPage("View All Books", new ViewBooksPanel(service));
        addPage("Search Books", new SearchBooksPanel(service));
        addPage("Issue Book", new IssueBookPanel(service));
        addPage("Return Book", new ReturnBookPanel(service));
        addPage("Members", new MemberPanel(service));
        addPage("About", new AboutPanel(service));
    }

    private void addPage(String name, JPanel panel) {
        pages.put(name, panel);
        contentPanel.add(panel, name);
    }

    /** Shows one screen, refreshes its data and highlights the menu button. */
    private void showPage(String name) {
        JPanel page = pages.get(name);
        if (page instanceof Refreshable) {
            ((Refreshable) page).refresh();
        }
        cardLayout.show(contentPanel, name);
        pageTitle.setText(name);

        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {
            boolean active = entry.getKey().equals(name);
            entry.getValue().setBackground(active ? Theme.MID_GREEN : Theme.DARK_GREEN);
        }
    }

    // ---------------- Background thread ----------------
    private void startReminderThread() {
        reminderTask = new ReminderTask(service, notificationLabel, 6);
        reminderThread = new Thread(reminderTask, "ReminderThread");
        reminderThread.setDaemon(true);      // stops automatically when the app closes
        reminderThread.start();
    }

    private void stopReminderThread() {
        if (reminderTask != null) {
            reminderTask.stop();
        }
        if (reminderThread != null) {
            reminderThread.interrupt();
        }
    }

    // ---------------- Logout / Exit ----------------
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to log out?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            stopReminderThread();
            dispose();
            new LoginFrame(service).setVisible(true);
        }
    }

    private void confirmExit() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                "Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            stopReminderThread();
            System.exit(0);
        }
    }
}
