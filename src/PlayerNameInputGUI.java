import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class PlayerNameInputGUI extends JDialog {

    public PlayerNameInputGUI(JFrame parent, int mode, Consumer<Player[]> onSubmit) {
        super(parent, "Εισαγωγή Ονομάτων", true);

        Player player1 = new Player(1);
        player1.readName();
        player1.readColour(null);

        Player player2;
        if (mode == 2) {
            player2 = new Player(2);
            player2.readName();
            player2.setColour(player1.getColour().equals("Blue") ? "Red" : "Blue");
        } else {
            player2 = new Player(2);
            player2.setName("Bot");
            player2.setColour(player1.getColour().equals("Blue") ? "Red" : "Blue");
        }

        onSubmit.accept(new Player[]{player1, player2});
        dispose();
    }
}