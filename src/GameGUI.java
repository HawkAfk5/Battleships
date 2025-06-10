import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameGUI extends JFrame {

    private StartingScreenGUI startingScreen;

    private JWindow backgroundWindow;
    
    private StatisticsGUI player1Stats = new StatisticsGUI();
    private StatisticsGUI player2Stats = new StatisticsGUI();

    private Board player1Board, player2Board;
    private boolean isPlayerOneTurn = true;
    private JButton[][] attackButtons;
    private JPanel boardPanel;
    private int[] shipSizes = {5, 4, 3, 3, 2};
    private int numOfPlayers;
    private Random random = new Random();
    
    private int boardSize;
    private String player1Name, player2Name;
    private Color player1Color, player2Color;

    public GameGUI(StartingScreenGUI startingScreen, int mode, int boardSize, String player1Name, Color player1Color,
            String player2Name, Color player2Color) {
    	
    	this.boardSize = boardSize;
        this.startingScreen = startingScreen;
        this.numOfPlayers = mode;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Color = player1Color;
        this.player2Color = player2Color;

        setTitle("Ναυμαχίες");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        toggleBackground(true);
        
        startGame();
    }

    private void startGame() {
        if (numOfPlayers == 2)
            setupBoards();
        else
            setupSinglePlayer();
    }

    private void setupBoards() {
        player1Board = new Board(boardSize);
        player2Board = new Board(boardSize);

        placeShipsForPlayer(player1Board, player1Name, player1Color, () -> {
        	 
        	toggleBackground(true); 
        	 
        	placeShipsForPlayer(player2Board, player2Name, player2Color, () -> {
                JOptionPane.showMessageDialog(this, "Έτοιμοι για μάχη! Ξεκινά ο " + player1Name);
                setupGameBoard();
            });
        });
    }


    private void setupSinglePlayer() {
        player1Board = new Board(boardSize);
        player2Board = new Board(boardSize);

        placeShipsForPlayer(player1Board, player1Name, player1Color, () -> {
            autoPlaceShips(player2Board);
            JOptionPane.showMessageDialog(this, "Ξεκινά το παιχνίδι ενάντια στον υπολογιστή!");
            setupGameBoard();
        });
    }

    private void toggleBackground(boolean show) {
        
        if (backgroundWindow == null) {
            backgroundWindow = new JWindow();
            try {
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/1st.png"));
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Image scaledImage = backgroundImage.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
                JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
                backgroundWindow.getContentPane().add(backgroundLabel);
                backgroundWindow.setSize(screenSize);
            } catch (Exception e) {
                System.err.println("Η εικόνα φόντου δεν βρέθηκε!");
                backgroundWindow = null; 
            }
        }

       
        if (backgroundWindow != null) {
            backgroundWindow.setVisible(show);
        }
    }

    private void autoPlaceShips(Board board) {
        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(boardSize);
                int col = random.nextInt(boardSize);
                boolean horizontal = random.nextBoolean();
                Ship ship = new Ship("Bot", size);
                placed = board.placeShip(ship, row, col, horizontal);
            }
        }
    }

    private void placeShipsForPlayer(Board board, String playerName, Color shipColor, Runnable onComplete) {
        JFrame frame = new JFrame("Τοποθέτηση Πλοίων - " + playerName);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        JPanel grid = new JPanel(new GridLayout(boardSize, boardSize));
        JButton[][] buttons = new JButton[boardSize][boardSize];

        int[] shipIndex = {0};

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton btn = new JButton();
                int r = row, c = col;
                btn.addActionListener(e -> {
                    if (shipIndex[0] >= shipSizes.length) return;

                    String[] options = {"Οριζόντια", "Κάθετα"};
                    int dir = JOptionPane.showOptionDialog(frame, "Κατεύθυνση για πλοίο " + shipSizes[shipIndex[0]],
                            "Κατεύθυνση", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if (dir == -1) return;
                    boolean horizontal = dir == 0;

                    Ship ship = new Ship("Πλοίο", shipSizes[shipIndex[0]]);
                    if (board.placeShip(ship, r, c, horizontal)) {
                        for (int i = 0; i < ship.getSize(); i++) {
                            int rr = r + (horizontal ? 0 : i);
                            int cc = c + (horizontal ? i : 0);
                            buttons[rr][cc].setBackground(shipColor); // χρήση χρώματος παίκτη
                        }
                        shipIndex[0]++;
                        if (shipIndex[0] >= shipSizes.length) {
                            frame.dispose();
                            onComplete.run();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Μη έγκυρη τοποθέτηση.");
                    }
                });

                buttons[row][col] = btn;
                grid.add(btn);
            }
        }

        frame.add(grid);
        frame.setVisible(true);
    }


    private void setupGameBoard() {
    	toggleBackground(true);
    
    	getContentPane().removeAll();
        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        attackButtons = new JButton[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton btn = new JButton();
                int r = row, c = col;
                btn.addActionListener(e -> handleAttack(r, c));
                attackButtons[row][col] = btn;
                boardPanel.add(btn);
            }
        }

        this.setVisible(true);
        add(boardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        updateBoardView();
    }

    private void updateBoardView() {
        Board defenderBoard = isPlayerOneTurn ? player2Board : player1Board;

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                JButton btn = attackButtons[row][col];
                boolean shot = defenderBoard.isShotAt(row, col);
                btn.setEnabled(isPlayerOneTurn || numOfPlayers == 2);
                if (shot) {
                    Ship ship = defenderBoard.getShipAt(row, col);
                    if (ship != null) {
                        btn.setBackground(ship.isSunk() ? Color.RED : Color.ORANGE);
                    } else {
                        btn.setBackground(Color.CYAN);
                    }
                } else {
                    btn.setBackground(null);
                }
            }
        }
    }

    private void handleAttack(int row, int col) {
        Board attackerBoard = isPlayerOneTurn ? player1Board : player2Board;
        Board defenderBoard = isPlayerOneTurn ? player2Board : player1Board;

        if (defenderBoard.isShotAt(row, col)) return;

        String result = defenderBoard.receiveAttack(row, col);
        JButton btn = attackButtons[row][col];
        btn.setEnabled(false);

        // Get the ship at attacked location (if any)
        Ship ship = defenderBoard.getShipAt(row, col);
        String shipName = (ship != null) ? ship.getName() : "None";

        // Update statistics
        if (isPlayerOneTurn) {
            player1Stats.recordShot(result.equals("HIT") || result.equals("SUNK"), shipName);
        } else {
            player2Stats.recordShot(result.equals("HIT") || result.equals("SUNK"), shipName);
        }

        switch (result) {
            case "HIT" -> {
                btn.setBackground(Color.ORANGE);
                SoundManager.playSound("hitsound.wav");
            }
            case "MISS" -> {
                btn.setBackground(Color.CYAN);
                SoundManager.playSound("misssound.wav");
            }
            case "SUNK" -> {
                btn.setBackground(Color.RED);
                SoundManager.playSound("sunksound.wav");
            }
        }

        if (defenderBoard.areAllShipsSunk()) {
            String winner = isPlayerOneTurn ? player1Name : (numOfPlayers == 1 ? "Ο Υπολογιστής" : player2Name);
            player1Stats.setWinner(player1Name); 
            player2Stats.setWinner(numOfPlayers == 1 ? "Υπολογιστής" : player2Name); 

            SwingUtilities.invokeLater(() -> {
                // Show a quick message
                JOptionPane.showMessageDialog(this, winner + " ΚΕΡΔΙΣΕ!");

                // Show detailed statistics
                

                askForRestart();
            });
            return;
        }

        if (numOfPlayers == 2) {
            if (result.equals("MISS")) {
                isPlayerOneTurn = !isPlayerOneTurn;
                JOptionPane.showMessageDialog(this, "Άστοχο! Σειρά του " + (isPlayerOneTurn ? player1Name : player2Name));
            } else {
                JOptionPane.showMessageDialog(this, "Εύστοχο! Ξαναρίξε " + (isPlayerOneTurn ? player1Name : player2Name));
            }
            updateBoardView();
        } else {
            isPlayerOneTurn = false;
            updateBoardView();
            botTurn();
        }
    }


    private void botTurn() {
        Timer timer = new Timer(1000, e -> {
            int row, col;
            do {
                row = random.nextInt(boardSize);
                col = random.nextInt(boardSize);
            } while (player1Board.isShotAt(row, col));

            String result = player1Board.receiveAttack(row, col);
            JButton btn = attackButtons[row][col];

            switch (result) {
                case "HIT" -> btn.setBackground(Color.ORANGE);
                case "MISS" -> btn.setBackground(Color.CYAN);
                case "SUNK" -> btn.setBackground(Color.RED);
            }

            if (player1Board.areAllShipsSunk()) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Ο υπολογιστής ΚΕΡΔΙΣΕ!");
                    new StatisticsGUI(player1Stats, player2Stats);
                    askForRestart();
                });
                return;
            }

            isPlayerOneTurn = true;
            updateBoardView();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void askForRestart() {
        this.setVisible(false);
    	JDialog dialog = new JDialog(this, "Τέλος Παιχνιδιού", false); // non-modal
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Θέλεις να ξαναπαίξεις;", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dialog.add(messageLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton yesButton = new JButton("Ναι");
        JButton noButton = new JButton("Όχι");
        JButton statsButton = new JButton("Προβολή Στατιστικών");

        yesButton.addActionListener(e -> {
            dialog.dispose();
            isPlayerOneTurn = true;
            startGame();
        });

        noButton.addActionListener(e -> {
            dialog.dispose();
            toggleBackground(false); 
            if (backgroundWindow != null) {
                 backgroundWindow.dispose(); 
            }
            System.exit(0);
        });

        statsButton.addActionListener(e -> {
            new StatisticsGUI(player1Stats, player2Stats);
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(statsButton);

        dialog.add(buttonPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }



}