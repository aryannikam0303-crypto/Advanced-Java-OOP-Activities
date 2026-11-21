import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Screen that shows all books in a JTable.
 */
public class ViewBooksPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final DefaultTableModel model = Theme.createModel(Theme.BOOK_COLUMNS);
    private final JTable table = new JTable(model);

    public ViewBooksPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 12));
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        Theme.styleTable(table);
        Theme.applyStatusRenderer(table, 4);

        JButton refreshButton = Theme.createButton("Refresh");
        refreshButton.addActionListener(e -> refresh());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setOpaque(false);
        top.add(refreshButton);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    @Override
    public void refresh() {
        Theme.fillBookTable(model, service.getAllBooks());
    }
}
