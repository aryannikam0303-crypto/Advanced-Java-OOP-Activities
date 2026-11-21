import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Login screen. Left side: green banner. Right side: login form.
 */
public class LoginFrame extends JFrame {

    private final LibraryService service;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;

    public LoginFrame(LibraryService service) {
        this.service = service;

        setTitle("Library Management System - Login");
        setSize(820, 460);
        setLocationRelativeTo(null);          // centre on screen
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.add(createBannerPanel());
        root.add(createFormPanel());
        setContentPane(root);
    }

    // ---------------- Left green banner ----------------
    private JPanel createBannerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.DARK_GREEN);

        JLabel icon = new JLabel("LMS", SwingConstants.CENTER);
        icon.setFont(Theme.TITLE_FONT.deriveFont(54f));
        icon.setForeground(Color.WHITE);

        JLabel title = new JLabel("Library Management", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage books, members and issues easily", SwingConstants.CENTER);
        subtitle.setFont(Theme.NORMAL_FONT);
        subtitle.setForeground(Theme.LIGHT_GREEN);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(5, 10, 5, 10);
        c.gridy = 0;
        panel.add(icon, c);
        c.gridy = 1;
        panel.add(title, c);
        c.gridy = 2;
        panel.add(subtitle, c);
        return panel;
    }

    // ---------------- Right login form ----------------
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("Admin Login");
        heading.setFont(Theme.TITLE_FONT);
        heading.setForeground(Theme.DARK_GREEN);

        usernameField = Theme.createTextField(18);
        passwordField = new JPasswordField(18);
        passwordField.setFont(Theme.NORMAL_FONT);
        passwordField.setBorder(usernameField.getBorder());
        final char defaultEcho = passwordField.getEchoChar();

        showPassword = new JCheckBox("Show password");
        showPassword.setBackground(Color.WHITE);
        showPassword.setFont(Theme.NORMAL_FONT);
        showPassword.addActionListener(e ->
                passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : defaultEcho));

        JButton loginButton = Theme.createButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        JButton exitButton = Theme.createSecondaryButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttons.setBackground(Color.WHITE);
        buttons.add(loginButton);
        buttons.add(javax.swing.Box.createRigidArea(new Dimension(10, 0)));
        buttons.add(exitButton);

        JLabel hint = new JLabel("Sample login: admin / admin123");
        hint.setFont(Theme.NORMAL_FONT);
        hint.setForeground(Theme.TEXT_GREY);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(6, 0, 6, 0);

        c.gridy = 0;
        c.insets = new Insets(0, 0, 20, 0);
        panel.add(heading, c);

        c.insets = new Insets(6, 0, 2, 0);
        c.gridy = 1;
        panel.add(Theme.createLabel("Username"), c);
        c.gridy = 2;
        c.insets = new Insets(0, 0, 8, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, c);

        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(6, 0, 2, 0);
        c.gridy = 3;
        panel.add(Theme.createLabel("Password"), c);
        c.gridy = 4;
        c.insets = new Insets(0, 0, 4, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, c);

        c.fill = GridBagConstraints.NONE;
        c.gridy = 5;
        panel.add(showPassword, c);
        c.gridy = 6;
        c.insets = new Insets(14, 0, 10, 0);
        panel.add(buttons, c);
        c.gridy = 7;
        c.insets = new Insets(4, 0, 0, 0);
        panel.add(hint, c);

        // Pressing ENTER triggers the Login button
        getRootPane().setDefaultButton(loginButton);
        return panel;
    }

    // ---------------- Event handling ----------------
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (service.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful. Welcome, Admin!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();                                   // close login window
            new DashboardFrame(service).setVisible(true); // open dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }
}
