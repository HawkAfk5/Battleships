import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // Δημιουργία παικτών
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        // Διάβασμα ονομάτων
        player1.readName();
        player2.readName();

        // Διάβασμα χρώματος από τον πρώτο παίκτη
        player1.readColour(""); // Ο πρώτος παίκτης διαλέγει ελεύθερα

        // Διάβασμα χρώματος από τον δεύτερο παίκτη, δεν μπορεί να επιλέξει το ίδιο
        player2.readColour(player1.getColour());

        // Εκτύπωση επιλογών
        JOptionPane.showMessageDialog(null,
            player1.getName() + " chose: " + player1.getColour() + "\n" +
            player2.getName() + " chose: " + player2.getColour()
        );
    }
}