import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameGUI extends JFrame {
    private int boardSize = 10;
    private Board player1Board, player2Board;
    private boolean isPlayerOneTurn = true;
    private JButton[][] attackButtons;
    private JPanel boardPanel;
    private int[] shipSizes = {5, 4, 3, 3, 2};
    private int numOfPlayers;
    private Random random = new Random();

    public GameGUI(int mode) {
        this.numOfPlayers = mode;
    	setTitle("Ναυμαχίες");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        placeShipsForPlayer(player1Board, "Παίκτης 1", () -> {
            placeShipsForPlayer(player2Board, "Παίκτης 2", () -> {
                JOptionPane.showMessageDialog(this, "Έτοιμοι για μάχη! Ξεκινά ο Παίκτης 1");
                setupGameBoard();
            });
        });
    }

    private void setupSinglePlayer() {
        player1Board = new Board(boardSize);
        player2Board = new Board(boardSize);

        placeShipsForPlayer(player1Board, "Παίκτης", () -> {
            autoPlaceShips(player2Board);
            JOptionPane.showMessageDialog(this, "Ξεκινά το παιχνίδι ενάντια στον υπολογιστή!");
            setupGameBoard();
        });
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

    private void placeShipsForPlayer(Board board, String playerName, Runnable onComplete) {
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
                            buttons[rr][cc].setBackground(Color.GRAY);
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

        switch (result) {
            case "HIT" -> btn.setBackground(Color.ORANGE);
            case "MISS" -> btn.setBackground(Color.CYAN);
            case "SUNK" -> btn.setBackground(Color.RED);
        }

        if (defenderBoard.areAllShipsSunk()) {
            String winner = isPlayerOneTurn ? "Παίκτης 1" : (numOfPlayers == 1 ? "Ο υπολογιστής" : "Παίκτης 2");
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, winner + " ΚΕΡΔΙΣΕ!");
                askForRestart();
            });
            return;
        }

        if (numOfPlayers == 2) {
            if (result.equals("MISS")) {
                isPlayerOneTurn = !isPlayerOneTurn;
                JOptionPane.showMessageDialog(this, "Άστοχο! Σειρά του " + (isPlayerOneTurn ? "Παίκτη 1" : "Παίκτη 2"));
            } else {
                JOptionPane.showMessageDialog(this, "Εύστοχο! Ξαναρίξε " + (isPlayerOneTurn ? "Παίκτη 1" : "Παίκτη 2"));
            }
            updateBoardView();
        } else {
            // Single player mode stays as is
            isPlayerOneTurn = !isPlayerOneTurn;
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
        int choice = JOptionPane.showConfirmDialog(this, "Θέλεις να ξαναπαίξεις;", "Νέα Παρτίδα;",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            isPlayerOneTurn = true;
            getContentPane().removeAll();
            revalidate();
            repaint();
            startGame();
            this.setVisible(false);
        } else {
            System.exit(0);
        }
    }

    
}