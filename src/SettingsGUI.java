import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SettingsGUI extends JFrame {

    //GUI components
    private JButton startingScreenButton;
    private JLabel grid;
    private JButton creditsButton;
    private JRadioButton tenRadio;
    private JRadioButton twelveRadio;
    private JRadioButton fifteenRadio;
    private JLabel sound;
    private JRadioButton onSoundRadio;
    private JRadioButton offSoundRadio;
    private JLabel music;
    private JRadioButton onMusicRadio;
    private JRadioButton offMusicRadio;

    public SettingsGUI() {
        //Φόρτωση background
        ImageIcon backgroundIcon = new ImageIcon("1st.png"); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        //Ρυθμίσεις γραμματοσειράς και μεγέθους
        Font bigFont = new Font("Arial", Font.BOLD, 50);
        Dimension buttonSize = new Dimension(400, 80);

        //Κουμπιά και επιλογές
        creditsButton = new JButton("CREDITS");
        startingScreenButton = new JButton("GO BACK");

        JButton[] buttons = { startingScreenButton, creditsButton };
        for (JButton btn : buttons) {
            btn.setFont(bigFont);
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        grid = new JLabel("GRID SIZE");
        sound = new JLabel("SOUND EFFECTS");
        music = new JLabel("MUSIC");
        
        grid.setFont(new Font("Arial", Font.BOLD, 70)); // μεγάλο μέγεθος
        grid.setForeground(Color.WHITE);                // λευκό χρώμα
        grid.setAlignmentX(Component.CENTER_ALIGNMENT); // κεντραρισμένο
        sound.setFont(new Font("Arial", Font.BOLD, 70)); // μεγάλο μέγεθος
        sound.setForeground(Color.WHITE);                // λευκό χρώμα
        sound.setAlignmentX(Component.CENTER_ALIGNMENT); // κεντραρισμένο
        music.setFont(new Font("Arial", Font.BOLD, 70)); // μεγάλο μέγεθος
        music.setForeground(Color.WHITE);                // λευκό χρώμα
        music.setAlignmentX(Component.CENTER_ALIGNMENT); // κεντραρισμένο
        
        tenRadio = new JRadioButton("10x10");
        twelveRadio = new JRadioButton("12x12");
        fifteenRadio = new JRadioButton("15x15");
        tenRadio.setSelected(true);

        tenRadio.setFont(bigFont);
        twelveRadio.setFont(bigFont);
        fifteenRadio.setFont(bigFont);
        tenRadio.setOpaque(false);
        twelveRadio.setOpaque(false);
        fifteenRadio.setOpaque(false);
        tenRadio.setForeground(Color.WHITE);
        twelveRadio.setForeground(Color.WHITE);
        fifteenRadio.setForeground(Color.WHITE);
        
        onSoundRadio = new JRadioButton("ON");
        offSoundRadio = new JRadioButton("OFF");
        onSoundRadio.setSelected(true);
        
        onSoundRadio.setFont(bigFont);
        offSoundRadio.setFont(bigFont);
        onSoundRadio.setOpaque(false);
        offSoundRadio.setOpaque(false);
        onSoundRadio.setForeground(Color.WHITE);
        offSoundRadio.setForeground(Color.WHITE);
        
        onMusicRadio = new JRadioButton("ON");
        offMusicRadio = new JRadioButton("OFF");
        onMusicRadio.setSelected(true);
        
        onMusicRadio.setFont(bigFont);
        offMusicRadio.setFont(bigFont);
        onMusicRadio.setOpaque(false);
        offMusicRadio.setOpaque(false);
        onMusicRadio.setForeground(Color.WHITE);
        offMusicRadio.setForeground(Color.WHITE);

        ButtonGroup radioGroup1 = new ButtonGroup();
        radioGroup1.add(tenRadio);
        radioGroup1.add(twelveRadio);
        radioGroup1.add(fifteenRadio);

        JPanel radioPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        radioPanel1.setOpaque(false);
        radioPanel1.add(tenRadio);
        radioPanel1.add(twelveRadio);
        radioPanel1.add(fifteenRadio);
        
        ButtonGroup radioGroup2 = new ButtonGroup();
        radioGroup2.add(onSoundRadio);
        radioGroup2.add(offSoundRadio);

        JPanel radioPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        radioPanel2.setOpaque(false);
        radioPanel2.add(onSoundRadio);
        radioPanel2.add(offSoundRadio);
        
        ButtonGroup radioGroup3 = new ButtonGroup();
        radioGroup3.add(onMusicRadio);
        radioGroup3.add(offMusicRadio);

        JPanel radioPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        radioPanel3.setOpaque(false);
        radioPanel3.add(onMusicRadio);
        radioPanel3.add(offMusicRadio);
        
        //Κεντρικό πάνελ
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));

        bottomPanel.add(Box.createVerticalGlue());
        bottomPanel.add(grid);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(radioPanel1);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(sound);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(radioPanel2);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(music);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(radioPanel3);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(creditsButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomPanel.add(startingScreenButton);
        bottomPanel.add(Box.createVerticalGlue());

        //Προσθήκη στο background
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        //Listeners
        creditsButton.addActionListener(new ButtonListener1());
        startingScreenButton.addActionListener(new ButtonListener2());

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
        	JOptionPane.showMessageDialog(null, "Tsormpatzoglou Nikolaos\nTopchanalis Panagiotis Tilemachos\nGeteridou Maria\nSidiropoulos Stefanos\nPetridis Kyrillos\nStellas Dimitrios\nPalios Nikolaos");
        }
    }

    class ButtonListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new StartingScreenGUI(); // Άνοιγμα ρυθμίσεων
            dispose();
        }
    }
}