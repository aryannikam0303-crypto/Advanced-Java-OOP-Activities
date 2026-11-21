import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Return an issued book. Shows all currently issued books.
 */
public class ReturnBookPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final JTextField bookIdField = Theme.createTextField(12);
    private final DefaultTableModel model = Theme.createModel(
            new String[]{"Book ID", "Title", "Issued To (Member ID)", "Member Name"});
    private final JTable table = new JTable(model);

    public ReturnBookPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 12));
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        Theme.styleTable(table);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (!e.getValueIsAdjusting() && row >= 0) {
                bookIdField.setText(String.valueOf(model.getValueAt(row, 0)));
            }
        });

        JButton returnButton = Theme.createButton("Return Book");
        returnButton.addActionListener(e -> returnBook());

        JPanel form = Theme.createCardPanel();
        form.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        form.add(Theme.createLabel("Book ID:"));
        form.add(bookIdField);
        form.add(returnButton);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.add(Theme.createHeading("Issued Books (click a row to select)"), BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        add(form, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void returnBook() {
        String bookId = bookIdField.getText().trim();
        if (bookId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Book ID to return.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            service.returnBook(bookId);
            JOptionPane.showMessageDialog(this,
                    "Book " + bookId.toUpperCase() + " returned successfully. It is now available.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            bookIdField.setText("");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Cannot Return Book", JOptionPane.ERROR_MESSAGE);
        } finally {
            refresh();
        }
    }

    @Override
    public void refresh() {
        model.setRowCount(0);
        List<Book> issued = service.getIssuedBookList();
        for (Book b : issued) {
            Member m = service.findMemberById(b.getIssuedTo());
            String memberName = (m == null) ? "-" : m.getName();
            model.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getIssuedTo(), memberName});
        }
    }
}
