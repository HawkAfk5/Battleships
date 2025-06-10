import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PreGameWindow extends JFrame {

	private StartingScreenGUI startingScreenGUI;
	private ArrayList<User> users;

    public PreGameWindow(ArrayList<User> users) {
    	this.users = users;
    	
    	//Ρυθμίσεις παραθύρου
        setTitle("Battleship");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        //Φόρτωση background
        ImageIcon backgroundIcon = new ImageIcon("2nd.png"); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        //Ρυθμίσεις γραμματοσειράς και μεγέθους
        Font bigFont = new Font("Arial", Font.BOLD, 50);
        Dimension buttonSize = new Dimension(500, 80);

        //Κουμπιά και επιλογές
        JButton loginButton = new JButton("LOGIN");
        JButton signupButton = new JButton("SIGNUP");
        JButton guestButton = new JButton("PLAY AS GUEST");
        JButton exitButton = new JButton("EXIT");

        JButton[] buttons = { loginButton, signupButton, guestButton, exitButton };
        for (JButton btn : buttons) {
        	btn.setBackground(new Color(70, 70, 70));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(bigFont);
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));

        bottomPanel.add(Box.createVerticalGlue());
        bottomPanel.add(loginButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(signupButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(guestButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(exitButton);
        bottomPanel.add(Box.createVerticalGlue());

        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(backgroundLabel);
        
        loginButton.addActionListener(e -> {
            Login login = new Login(this, users);
            login.setVisible(true);
        });

        signupButton.addActionListener(e -> {
            Signup signup = new Signup(this, users);
            signup.setVisible(true);
        });

        guestButton.addActionListener(e -> {
            setVisible(false);
            if (startingScreenGUI == null) {
                startingScreenGUI = new StartingScreenGUI(users);
            } else {
                startingScreenGUI.setVisible(true);
            }
            
        });

        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}