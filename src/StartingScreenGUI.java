import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class StartingScreenGUI extends JFrame {

	private RulesGUI rulesGUI;
    private SettingsGUI settingsGUI;
    private GameGUI gameGUI;
    private ArrayList<User> users;
	
    //GUI components
    private JButton playButton;
    private JButton settingsButton;
    private JButton rulesButton;
    private JButton logoutButton;
    private JRadioButton onePlayerRadio;
    private JRadioButton twoPlayersRadio;

    public StartingScreenGUI(ArrayList<User> users) {
    	this.users = users;
    	
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
        logoutButton = new JButton("LOGOUT");

        JButton[] buttons = { playButton, settingsButton, rulesButton, logoutButton };
        for (JButton btn : buttons) {
        	btn.setBackground(new Color(70, 70, 70));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
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
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(radioPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(settingsButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(rulesButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(logoutButton);
        bottomPanel.add(Box.createVerticalGlue());

        //Προσθήκη στο background
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        //Listeners
        playButton.addActionListener(new ButtonListener1());
        settingsButton.addActionListener(new ButtonListener2());
        rulesButton.addActionListener(new ButtonListener3());
        logoutButton.addActionListener(new ButtonListener4());

        //Ρυθμίσεις παραθύρου
        setContentPane(backgroundLabel);
        setTitle("Battleship");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
SoundManager.playBackgroundMusic("Main Theme.wav");
    }

    //Listeners

    class ButtonListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	int mode = onePlayerRadio.isSelected() ? 1 : 2;
        	int boardSize = settingsGUI != null ? settingsGUI.getBoardSize() : 10;
        	new PlayerNameInputGUI(StartingScreenGUI.this, mode, players -> {
                dispose();
                new GameGUI(StartingScreenGUI.this, mode, boardSize,
                    players[0].getName(),
                    players[0].getColour().equals("Blue") ? Color.BLUE : Color.RED,
                    players[1].getName(),
                    players[1].getColour().equals("Blue") ? Color.BLUE : Color.RED
                );
            });
        }
    }

    class ButtonListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	if (settingsGUI == null) {
        		settingsGUI = new SettingsGUI(StartingScreenGUI.this);
            } else {
            	settingsGUI.setVisible(true);
            } // Άνοιγμα ρυθμίσεων
        	(StartingScreenGUI.this).setVisible(false);
        }
    }

    class ButtonListener3 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	if (rulesGUI == null) {
        		rulesGUI = new RulesGUI(StartingScreenGUI.this);
            } else {
            	rulesGUI.setVisible(true);
            } // Άνοιγμα κανόνων
            (StartingScreenGUI.this).setVisible(false);
        }
    }
    
    class ButtonListener4 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	new PreGameWindow(users);
        	dispose();
        }
    }
}
