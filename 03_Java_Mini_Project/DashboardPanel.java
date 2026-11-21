import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Home screen: four summary cards + recent activity list.
 */
public class DashboardPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final JLabel totalBooksLabel = new JLabel("0");
    private final JLabel availableLabel = new JLabel("0");
    private final JLabel issuedLabel = new JLabel("0");
    private final JLabel membersLabel = new JLabel("0");
    private final DefaultListModel<String> activityModel = new DefaultListModel<>();

    public DashboardPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 20));
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        JPanel cards = new JPanel(new GridLayout(1, 4, 18, 0));
        cards.setOpaque(false);
        cards.add(createCard("Total Books", totalBooksLabel, Theme.DARK_GREEN));
        cards.add(createCard("Available Books", availableLabel, Theme.MID_GREEN));
        cards.add(createCard("Issued Books", issuedLabel, new Color(230, 126, 34)));
        cards.add(createCard("Registered Members", membersLabel, new Color(52, 110, 170)));

        JList<String> activityList = new JList<>(activityModel);
        activityList.setFont(Theme.NORMAL_FONT);
        activityList.setFixedCellHeight(30);

        JScrollPane scroll = new JScrollPane(activityList);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel activityCard = Theme.createCardPanel();
        activityCard.setLayout(new BorderLayout(0, 10));
        activityCard.add(Theme.createHeading("Recent Activity"), BorderLayout.NORTH);
        activityCard.add(scroll, BorderLayout.CENTER);

        add(cards, BorderLayout.NORTH);
        add(activityCard, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 0, 0, 0, accent),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.BOLD_FONT);
        titleLabel.setForeground(Theme.TEXT_GREY);

        valueLabel.setFont(Theme.TITLE_FONT.deriveFont(38f));
        valueLabel.setForeground(accent);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    @Override
    public void refresh() {
        totalBooksLabel.setText(String.valueOf(service.getTotalBooks()));
        availableLabel.setText(String.valueOf(service.getAvailableBooks()));
        issuedLabel.setText(String.valueOf(service.getIssuedBooks()));
        membersLabel.setText(String.valueOf(service.getTotalMembers()));

        activityModel.clear();
        List<String> activity = service.getRecentActivity();
        for (String item : activity) {
            activityModel.addElement(item);
        }
    }
}
