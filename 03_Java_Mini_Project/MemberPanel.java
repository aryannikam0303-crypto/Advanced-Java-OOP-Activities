import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Member management: register new members and view all members.
 */
public class MemberPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final JTextField idField = Theme.createTextField(16);
    private final JTextField nameField = Theme.createTextField(16);
    private final JTextField emailField = Theme.createTextField(16);
    private final JTextField phoneField = Theme.createTextField(16);
    private final DefaultTableModel model = Theme.createModel(
            new String[]{"Member ID", "Name", "Email", "Phone", "Books Issued"});
    private final JTable table = new JTable(model);

    public MemberPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(20, 0));
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        Theme.styleTable(table);

        idField.setEditable(false);                    // ID is generated automatically
        idField.setBackground(Theme.LIGHT_GREY);

        JPanel form = Theme.createCardPanel();
        form.setLayout(new GridBagLayout());
        form.setPreferredSize(new Dimension(340, 0));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 5, 10, 5);
        form.add(Theme.createHeading("Register Member"), c);

        Theme.addFormRow(form, 1, "Member ID", idField);
        Theme.addFormRow(form, 2, "Name *", nameField);
        Theme.addFormRow(form, 3, "Email *", emailField);
        Theme.addFormRow(form, 4, "Phone *", phoneField);

        JButton registerButton = Theme.createButton("Register");
        registerButton.addActionListener(e -> registerMember());
        JButton clearButton = Theme.createSecondaryButton("Clear");
        clearButton.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(registerButton);
        buttons.add(clearButton);

        c.gridy = 5;
        c.insets = new Insets(14, 5, 0, 5);
        form.add(buttons, c);

        // push the form to the top of its card
        c.gridy = 6;
        c.weighty = 1.0;
        form.add(new JPanel() {{ setOpaque(false); }}, c);

        add(form, BorderLayout.WEST);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void registerMember() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        // ----- Input validation -----
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Email and Phone are required.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!name.matches("[A-Za-z .]+")) {
            JOptionPane.showMessageDialog(this, "Name may contain only letters, spaces and dots.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String id = idField.getText().trim();
            service.addMember(id, name, email, phone);
            JOptionPane.showMessageDialog(this, "Member registered successfully with ID " + id + ".",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Registration Failed", JOptionPane.ERROR_MESSAGE);
        } finally {
            refresh();
        }
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        idField.setText(service.generateMemberId());
        nameField.requestFocus();
    }

    @Override
    public void refresh() {
        idField.setText(service.generateMemberId());
        model.setRowCount(0);
        List<Member> list = service.getAllMembers();
        for (Member m : list) {
            model.addRow(new Object[]{m.getId(), m.getName(), m.getEmail(),
                    m.getPhone(), m.getBooksIssued() + " / " + Member.MAX_BOOKS});
        }
    }
}
