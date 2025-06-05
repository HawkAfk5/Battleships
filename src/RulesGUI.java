import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RulesGUI extends JFrame {

    public RulesGUI() {
        setTitle("Battleship Rules");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Background image
        ImageIcon backgroundIcon = new ImageIcon("1st.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        // Transparent overlay panel
        JPanel overlayPanel = new JPanel(new BorderLayout(20, 20));
        overlayPanel.setOpaque(false);
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Title label
        JLabel title = new JLabel("Rules of Battleship", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        overlayPanel.add(title, BorderLayout.NORTH);

        // Rules text
        JTextArea rulesText = new JTextArea(
            "Battleship is a strategic game where two players compete to sink each other's fleet.\n\n" +

            "Placing Ships:\n" +
            "- Ships must be placed within the grid boundaries.\n" +
            "- Ships cannot overlap each other.\n\n" +

            "Attacking:\n" +
            "- On their turn, each player selects a location to attack.\n" +
            "- If the attack hits an enemy ship, the player gets another turn.\n" +
            "- Attacking the same spot more than once is not allowed.\n\n" +

            "End of Game:\n" +
            "- The game ends when all of one player's ships are sunk.\n\n" +

            "Have fun!"
        );
        rulesText.setFont(new Font("Arial", Font.PLAIN, 18));
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setOpaque(false);
        rulesText.setForeground(Color.BLACK);
        rulesText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(rulesText);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        overlayPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back to Starting Screen");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(new Color(70, 70, 70));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(300, 50));
        backButton.addActionListener(e -> {
            new StartingScreenGUI();
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        overlayPanel.add(buttonPanel, BorderLayout.SOUTH);

        backgroundLabel.add(overlayPanel, BorderLayout.CENTER);
        setContentPane(backgroundLabel);
        setVisible(true);
    }
}
