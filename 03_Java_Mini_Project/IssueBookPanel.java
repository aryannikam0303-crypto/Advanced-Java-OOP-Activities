import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Issue a book to a member. Shows the list of available books;
 * clicking a row fills the Book ID field automatically.
 */
public class IssueBookPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final JTextField bookIdField = Theme.createTextField(12);
    private final JTextField memberIdField = Theme.createTextField(12);
    private final DefaultTableModel model = Theme.createModel(Theme.BOOK_COLUMNS);
    private final JTable table = new JTable(model);

    public IssueBookPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 12));
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        Theme.styleTable(table);
        Theme.applyStatusRenderer(table, 4);

        // Clicking a row copies its Book ID into the text field
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (!e.getValueIsAdjusting() && row >= 0) {
                bookIdField.setText(String.valueOf(model.getValueAt(row, 0)));
            }
        });

        JButton issueButton = Theme.createButton("Issue Book");
        issueButton.addActionListener(e -> issueBook());

        JPanel form = Theme.createCardPanel();
        form.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        form.add(Theme.createLabel("Book ID:"));
        form.add(bookIdField);
        form.add(Theme.createLabel("Member ID:"));
        form.add(memberIdField);
        form.add(issueButton);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.add(Theme.createHeading("Available Books (click a row to select)"), BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        add(form, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void issueBook() {
        String bookId = bookIdField.getText().trim();
        String memberId = memberIdField.getText().trim();

        if (bookId.isEmpty() || memberId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Book ID and Member ID.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            service.issueBook(bookId, memberId);
            JOptionPane.showMessageDialog(this,
                    "Book " + bookId.toUpperCase() + " issued to member " + memberId.toUpperCase() + ".",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            bookIdField.setText("");
            memberIdField.setText("");
        } catch (BookNotAvailableException ex) {                       // our custom exception
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Book Not Available", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Cannot Issue Book", JOptionPane.ERROR_MESSAGE);
        } finally {
            refresh();                      // always update the table
        }
    }

    @Override
    public void refresh() {
        Theme.fillBookTable(model, service.getAvailableBookList());
    }
}
