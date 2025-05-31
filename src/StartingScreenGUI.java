import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartingScreenGUI extends JFrame {

    //GUI components
    private JButton playButton;
    private JButton settingsButton;
    private JButton rulesButton;
    private JRadioButton onePlayerRadio;
    private JRadioButton twoPlayersRadio;

    public StartingScreenGUI() {
        //Φόρτωση background
        ImageIcon backgroundIcon = new ImageIcon("2nd.png"); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        //Ρυθμίσεις γραμματοσειράς και μεγέθους
        Font bigFont = new Font("Arial", Font.BOLD, 50);
        Dimension buttonSize = new Dimension(400, 80);

        //Κουμπιά και επιλογές
        playButton = new JButton("PLAY");
        settingsButton = new JButton("SETTINGS");
        rulesButton = new JButton("RULES");

        JButton[] buttons = { playButton, settingsButton, rulesButton };
        for (JButton btn : buttons) {
            btn.setFont(bigFont);
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        onePlayerRadio = new JRadioButton("1 PLAYER");
        twoPlayersRadio = new JRadioButton("2 PLAYERS");
        onePlayerRadio.setSelected(true);

        onePlayerRadio.setFont(bigFont);
        twoPlayersRadio.setFont(bigFont);
        onePlayerRadio.setOpaque(false);
        twoPlayersRadio.setOpaque(false);
        onePlayerRadio.setForeground(Color.WHITE);
        twoPlayersRadio.setForeground(Color.WHITE);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(onePlayerRadio);
        radioGroup.add(twoPlayersRadio);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        radioPanel.setOpaque(false);
        radioPanel.add(onePlayerRadio);
        radioPanel.add(twoPlayersRadio);

        //Κεντρικό πάνελ
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));

        bottomPanel.add(Box.createVerticalGlue());
        bottomPanel.add(playButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(radioPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(settingsButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(rulesButton);
        bottomPanel.add(Box.createVerticalGlue());

        //Προσθήκη στο background
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        //Listeners
        playButton.addActionListener(new ButtonListener1());
        settingsButton.addActionListener(new ButtonListener2());
        rulesButton.addActionListener(new ButtonListener3());

        //Ρυθμίσεις παραθύρου
        setContentPane(backgroundLabel);
        setTitle("Battleship");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    //Listeners

    class ButtonListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	int mode = onePlayerRadio.isSelected() ? 1 : 2;
            //new GameGUI(mode); // Άνοιγμα παιχνιδιού
            dispose();
        }
    }

    class ButtonListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SettingsGUI(); // Άνοιγμα ρυθμίσεων
            dispose();
        }
    }

    class ButtonListener3 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RulesGUI(); // Άνοιγμα κανόνων
            dispose();
        }
    }
}
