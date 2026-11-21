import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Search books by ID, title or author (partial, case-insensitive).
 */
public class SearchBooksPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final JComboBox<String> fieldBox = new JComboBox<>(new String[]{"Title", "Author", "ID"});
    private final JTextField keywordField = Theme.createTextField(24);
    private final DefaultTableModel model = Theme.createModel(Theme.BOOK_COLUMNS);
    private final JTable table = new JTable(model);

    public SearchBooksPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 12));
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        Theme.styleTable(table);
        Theme.applyStatusRenderer(table, 4);
        fieldBox.setFont(Theme.NORMAL_FONT);

        JButton searchButton = Theme.createButton("Search");
        searchButton.addActionListener(e -> search());
        JButton showAllButton = Theme.createSecondaryButton("Show All");
        showAllButton.addActionListener(e -> {
            keywordField.setText("");
            refresh();
        });
        // pressing ENTER in the text box also searches
        keywordField.addActionListener(e -> search());

        JPanel top = Theme.createCardPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.add(Theme.createLabel("Search by:"));
        top.add(fieldBox);
        top.add(keywordField);
        top.add(searchButton);
        top.add(showAllButton);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void search() {
        String keyword = keywordField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search keyword.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String field = (String) fieldBox.getSelectedItem();
        List<Book> results = service.searchBooks(keyword, field);
        Theme.fillBookTable(model, results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found for '" + keyword + "'.",
                    "No Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void refresh() {
        Theme.fillBookTable(model, service.getAllBooks());
    }
}
