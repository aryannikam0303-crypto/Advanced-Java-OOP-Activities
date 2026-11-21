import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * About screen: project info, OOP concepts, and a polymorphism demo
 * (list of Person objects printed through overridden methods).
 */
public class AboutPanel extends JPanel implements Refreshable {

    private final LibraryService service;
    private final JTextArea textArea = new JTextArea();

    public AboutPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(Theme.LIGHT_GREY);
        setBorder(Theme.pagePadding());

        textArea.setEditable(false);
        textArea.setFont(Theme.NORMAL_FONT);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel card = Theme.createCardPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Theme.BORDER_GREY));
        card.add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
    }

    @Override
    public void refresh() {
        StringBuilder sb = new StringBuilder();
        sb.append("LIBRARY MANAGEMENT SYSTEM\n");
        sb.append("Advanced Java OOP Project - Core Java, Swing and AWT\n\n");

        sb.append("OOP concepts used\n");
        sb.append("  - Inheritance    : Person -> Member, Person -> Librarian\n");
        sb.append("  - Polymorphism   : getRole() / getDetails() overridden in each subclass\n");
        sb.append("  - Abstraction    : Person is an abstract class with abstract methods\n");
        sb.append("  - Interface      : Borrowable, implemented by Book\n");
        sb.append("  - Encapsulation  : private fields with getters and setters\n\n");

        sb.append("Advanced Java\n");
        sb.append("  - Exceptions     : try-catch-finally, BookNotAvailableException\n");
        sb.append("  - Multithreading : ReminderTask (Runnable) updates the status bar\n");
        sb.append("  - Collections    : ArrayList and HashMap in LibraryService\n\n");

        sb.append("Runtime polymorphism demo (people in the system)\n");
        sb.append(service.getPeopleSummary());

        textArea.setText(sb.toString());
        textArea.setCaretPosition(0);
    }
}
