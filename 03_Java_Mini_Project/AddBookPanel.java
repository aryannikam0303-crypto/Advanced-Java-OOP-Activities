import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Screen to add a new book. Validates input and prevents duplicate IDs.
 */
public class AddBookPanel extends JPanel {

    private final LibraryService service;
    private final JTextField idField = Theme.createTextField(22);
    private final JTextField titleField = Theme.createTextField(22);
    private final JTextField authorField = Theme.createTextField(22);
    private final JComboBox<String> categoryBox = new JComboBox<>(new String[]{
            "Programming", "Technology", "Science", "Fiction", "History", "Biography", "Other"});

    public AddBookPanel(LibraryService service) {
        this.service = service;
        setLayout(new GridBagLayout());          // centres the form card
        setBackground(Theme.LIGHT_GREY);

        categoryBox.setFont(Theme.NORMAL_FONT);
        categoryBox.setBackground(Color.WHITE);

        JPanel card = Theme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 400));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new java.awt.Insets(0, 5, 12, 5);
        card.add(Theme.createHeading("Enter Book Details"), c);

        Theme.addFormRow(card, 1, "Book ID *", idField);
        Theme.addFormRow(card, 2, "Title *", titleField);
        Theme.addFormRow(card, 3, "Author *", authorField);
        Theme.addFormRow(card, 4, "Category", categoryBox);

        JButton addButton = Theme.createButton("Add Book");
        addButton.addActionListener(e -> addBook());
        JButton clearButton = Theme.createSecondaryButton("Clear");
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(addButton);
        buttons.add(clearButton);

        c.gridy = 5;
        c.insets = new java.awt.Insets(18, 5, 0, 5);
        card.add(buttons, c);

        add(card);
    }

    private void addBook() {
        String id = idField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = (String) categoryBox.getSelectedItem();

        // ----- Input validation -----
        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book ID, Title and Author are required.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!id.matches("[A-Za-z0-9-]+")) {
            JOptionPane.showMessageDialog(this,
                    "Book ID may contain only letters, digits and hyphen (example: B107).",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ----- Exception handling: try-catch-finally -----
        try {
            service.addBook(id.toUpperCase(), title, author, category);
            JOptionPane.showMessageDialog(this, "Book added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Duplicate Book ID", JOptionPane.ERROR_MESSAGE);
        } finally {
            idField.requestFocus();              // always runs
        }
    }

    private void clearForm() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        categoryBox.setSelectedIndex(0);
        idField.requestFocus();
    }
}
