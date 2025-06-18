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
        setTitle("Battleship Statistics");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        ImageIcon backgroundIcon = new ImageIcon("1st.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        JPanel overlayPanel = new JPanel(new BorderLayout(30, 30));
        overlayPanel.setOpaque(false);
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel titleLabel = new JLabel("Battleship Statistics!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
        titleLabel.setForeground(Color.WHITE);
        overlayPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        mainPanel.setOpaque(false);

        String winnerName = player1Stats.getWinner();
        boolean isPlayer1Winner = winnerName.equals("PLAYER 1") || winnerName.equals("HUMAN");

        String player2Label = isPlayer1Winner ? "WINNER" : "LOSER";
        String player1Label = isPlayer1Winner ? "LOSER" : "WINNER";

        mainPanel.add(createPlayerPanel(player1Stats, player1Label));
        mainPanel.add(createPlayerPanel(player2Stats, player2Label));

        overlayPanel.add(mainPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("CLOSE");
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

    private JPanel createPlayerPanel(StatisticsGUI stats, String playerLabelStr) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel playerLabel = new JLabel(playerLabelStr);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        playerLabel.setForeground(playerLabelStr.equals("WINNER") ? new Color(0, 255, 0) : new Color(255, 80, 80));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(playerLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(createStatLabel("Total number of moves: " + stats.getTurnsTaken()));
        panel.add(createStatLabel(String.format("Hit Ratio: %.2f%%", stats.getAccuracy())));
        panel.add(createStatLabel("Player Name: " + (stats.getWinner().isEmpty() ? "-" : stats.getWinner())));
        panel.add(createStatLabel("Max consecutive hits: " + stats.getMaxHitStreak()));
        panel.add(createStatLabel("Max consecutive misses: " + stats.getMaxMissStreak()));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel shipsLabel = new JLabel("Total Ships Hit :");
        shipsLabel.setFont(new Font("Arial", Font.BOLD, 28));
        shipsLabel.setForeground(Color.WHITE);
        shipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(shipsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        if (stats.getShotsPerShip().isEmpty()) {
            panel.add(createStatLabel("  (No hits)"));
        } else {
            for (Map.Entry<String, Integer> entry : stats.getShotsPerShip().entrySet()) {
                panel.add(createStatLabel("  " + entry.getKey() + ": " + entry.getValue()));
            }
        }

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
