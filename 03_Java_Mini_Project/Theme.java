import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Central place for colours, fonts and small helper methods,
 * so every screen has the same dark green / white / grey look.
 */
public class Theme {

    public static final Color DARK_GREEN  = new Color(27, 94, 32);
    public static final Color MID_GREEN   = new Color(46, 125, 50);
    public static final Color LIGHT_GREEN = new Color(220, 237, 221);
    public static final Color LIGHT_GREY  = new Color(243, 244, 246);
    public static final Color BORDER_GREY = new Color(215, 218, 222);
    public static final Color TEXT_DARK   = new Color(33, 37, 41);
    public static final Color TEXT_GREY   = new Color(108, 117, 125);
    public static final Color RED         = new Color(183, 28, 28);

    public static final Font TITLE_FONT   = new Font("SansSerif", Font.BOLD, 26);
    public static final Font HEADING_FONT = new Font("SansSerif", Font.BOLD, 18);
    public static final Font NORMAL_FONT  = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BOLD_FONT    = new Font("SansSerif", Font.BOLD, 14);

    private Theme() { }     // utility class - no objects needed

    // ---------------- Basic components ----------------

    public static JButton createButton(String text) {
        return createButton(text, DARK_GREEN, Color.WHITE);
    }

    public static JButton createButton(String text, final Color background, Color foreground) {
        final JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(background.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }
        });
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        return createButton(text, new Color(108, 117, 125), Color.WHITE);
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(NORMAL_FONT);
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JLabel createHeading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADING_FONT);
        label.setForeground(DARK_GREEN);
        return label;
    }

    public static JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(NORMAL_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GREY),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return field;
    }

    /** White rounded-look card with a thin grey border. */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GREY),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));
        return panel;
    }

    public static Border pagePadding() {
        return BorderFactory.createEmptyBorder(20, 25, 20, 25);
    }

    /** Adds "Label : [field]" as one row of a GridBagLayout form. */
    public static void addFormRow(JPanel panel, int row, String labelText, JComponent field) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 5, 8, 15);
        panel.add(createLabel(labelText), c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 0, 8, 5);
        panel.add(field, c);
    }

    // ---------------- Tables ----------------

    public static void styleTable(JTable table) {
        table.setFont(NORMAL_FONT);
        table.setRowHeight(30);
        table.setGridColor(BORDER_GREY);
        table.setSelectionBackground(LIGHT_GREEN);
        table.setSelectionForeground(TEXT_DARK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(BOLD_FONT);
        header.setBackground(DARK_GREEN);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 34));
        header.setReorderingAllowed(false);
    }

    /** A table model whose cells cannot be edited by the user. */
    public static DefaultTableModel createModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static final String[] BOOK_COLUMNS =
            {"Book ID", "Title", "Author", "Category", "Status", "Issued To"};

    /** Clears the model and fills it with the given books. */
    public static void fillBookTable(DefaultTableModel model, List<Book> books) {
        model.setRowCount(0);
        for (Book b : books) {
            model.addRow(new Object[]{
                    b.getBookId(), b.getTitle(), b.getAuthor(), b.getCategory(),
                    b.getStatus(), b.getIssuedTo().isEmpty() ? "-" : b.getIssuedTo()
            });
        }
    }

    /** Colours the "Status" column: green = Available, red = Issued. */
    public static void applyStatusRenderer(JTable table, int statusColumn) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable t, Object value,
                    boolean selected, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, value, selected, focus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(BOLD_FONT);
                if ("Available".equals(value)) {
                    setForeground(MID_GREEN);
                } else {
                    setForeground(RED);
                }
                return this;
            }
        };
        table.getColumnModel().getColumn(statusColumn).setCellRenderer(renderer);
    }
}
