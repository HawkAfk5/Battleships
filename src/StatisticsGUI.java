import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticsGUI extends JFrame {

    private int turnsTaken = 0;
    private int hits = 0;
    private int misses = 0;
    private String winner = "";
    private HashMap<String, Integer> shotsPerShip = new HashMap<>();

    private int currentHitStreak = 0;
    private int maxHitStreak = 0;
    private int currentMissStreak = 0;
    private int maxMissStreak = 0;

    public StatisticsGUI() {}

    public void recordShot(boolean hit, String shipName) {
        turnsTaken++;
        if (hit) {
            hits++;
            currentHitStreak++;
            maxHitStreak = Math.max(maxHitStreak, currentHitStreak);
            currentMissStreak = 0;

            if (!shipName.equals("None")) {
                shotsPerShip.put(shipName, shotsPerShip.getOrDefault(shipName, 0) + 1);
            }
        } else {
            misses++;
            currentMissStreak++;
            maxMissStreak = Math.max(maxMissStreak, currentMissStreak);
            currentHitStreak = 0;
        }
    }

    public double getAccuracy() {
        int totalShots = hits + misses;
        return totalShots == 0 ? 0 : (hits * 100.0) / totalShots;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getTurnsTaken() { return turnsTaken; }
    public String getWinner() { return winner; }
    public HashMap<String, Integer> getShotsPerShip() { return shotsPerShip; }
    public int getMaxHitStreak() { return maxHitStreak; }
    public int getMaxMissStreak() { return maxMissStreak; }


    public StatisticsGUI(StatisticsGUI player1Stats, StatisticsGUI player2Stats) {
        setTitle("Στατιστικά Ναυμαχίας");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        // Background image
        ImageIcon backgroundIcon = new ImageIcon("1st.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        // Transparent overlay panel with padding
        JPanel overlayPanel = new JPanel(new BorderLayout(30, 30));
        overlayPanel.setOpaque(false);
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Title label
        JLabel titleLabel = new JLabel("Στατιστικά Ναυμαχίας", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
        titleLabel.setForeground(Color.WHITE);
        overlayPanel.add(titleLabel, BorderLayout.NORTH);

        // Main panel with two player stats side-by-side
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        mainPanel.setOpaque(false);

        mainPanel.add(createPlayerPanel(player1Stats, "Παίκτης 1"));
        mainPanel.add(createPlayerPanel(player2Stats, player2Stats.getWinner().equals("Υπολογιστής") ? "Υπολογιστής" : "Παίκτης 2"));

        overlayPanel.add(mainPanel, BorderLayout.CENTER);

        // Close button panel
        JButton closeButton = new JButton("ΚΛΕΙΣΙΜΟ");
        closeButton.setFont(new Font("Arial", Font.BOLD, 50));
        closeButton.setBackground(new Color(70, 70, 70));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(300, 80));
        closeButton.addActionListener(e -> this.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        overlayPanel.add(buttonPanel, BorderLayout.SOUTH);

        backgroundLabel.add(overlayPanel, BorderLayout.CENTER);

        setContentPane(backgroundLabel);
        setVisible(true);
    }

    private JPanel createPlayerPanel(StatisticsGUI stats, String playerName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel playerLabel = new JLabel(playerName);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(playerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(createStatLabel("Συνολικές κινήσεις: " + stats.getTurnsTaken()));
        panel.add(createStatLabel(String.format("Ποσοστό Χτυπημάτων: %.2f%%", stats.getAccuracy())));
        panel.add(createStatLabel("Νικητής: " + (stats.getWinner().isEmpty() ? "-" : stats.getWinner())));
        panel.add(createStatLabel("Μέγιστο Σερί Χτυπημάτων: " + stats.getMaxHitStreak()));
        panel.add(createStatLabel("Μέγιστο Σερί Αστοχιών: " + stats.getMaxMissStreak()));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel shipsLabel = new JLabel("Χτυπήματα ανά Πλοίο:");
        shipsLabel.setFont(new Font("Arial", Font.BOLD, 28));
        shipsLabel.setForeground(Color.WHITE);
        shipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(shipsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextArea shipsText = new JTextArea(10, 20);
        shipsText.setEditable(false);
        shipsText.setFont(new Font("Monospaced", Font.PLAIN, 18));
        shipsText.setOpaque(true);
        shipsText.setBackground(new Color(40, 40, 40, 200));
        shipsText.setForeground(Color.WHITE);
        shipsText.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        if (stats.getShotsPerShip().isEmpty()) {
            shipsText.setText("  (Κανένα χτύπημα)");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : stats.getShotsPerShip().entrySet()) {
                sb.append(String.format("  %s: %d\n", entry.getKey(), entry.getValue()));
            }
            shipsText.setText(sb.toString());
        }

        JScrollPane scrollPane = new JScrollPane(shipsText);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panel.add(scrollPane);

        return panel;
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }
}
