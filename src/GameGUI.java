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
    private int numOfPlayers = 2;  // default 2

    public GameGUI() {
        setTitle("Ναυμαχίες");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        askPlayerCount();
    }

    private void askPlayerCount() {
        String[] options = {"1 Παίκτης", "2 Παίκτες"};
        int choice = JOptionPane.showOptionDialog(this,
                "Επέλεξε αριθμό παικτών",
                "Ναυμαχίες",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        numOfPlayers = (choice == 0) ? 1 : 2;

        if (numOfPlayers == 2) {
            setupBoards();
        } else {
            JOptionPane.showMessageDialog(this, "Η λειτουργία για 1 παίκτη θα προστεθεί σύντομα!");
        }
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
                btn.setEnabled(!shot);
                if (shot) {
                    Ship ship = defenderBoard.getShipAt(row, col);
                    btn.setBackground(ship != null ? Color.ORANGE : Color.CYAN);
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
            case "HIT":
                btn.setBackground(Color.ORANGE);
                break;
            case "MISS":
                btn.setBackground(Color.CYAN);
                break;
            case "SUNK":
                btn.setBackground(Color.RED);
                break;
        }

        if (defenderBoard.areAllShipsSunk()) {
            String winner = isPlayerOneTurn ? "Παίκτης 1" : "Παίκτης 2";
            JOptionPane.showMessageDialog(this, winner + " ΚΕΡΔΙΣΕ!");
            System.exit(0);
        }

        isPlayerOneTurn = !isPlayerOneTurn;
        JOptionPane.showMessageDialog(this, "Σειρά του " + (isPlayerOneTurn ? "Παίκτη 1" : "Παίκτη 2"));
        updateBoardView();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            gui.setVisible(true);
        });
    }
}