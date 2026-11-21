/**
 * Small interface implemented by every screen (panel) that shows data.
 * DashboardFrame calls refresh() each time a screen is opened,
 * so the data on screen is always up to date.
 */
public interface Refreshable {
    void refresh();
}
