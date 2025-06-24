import javax.swing.JOptionPane;


public class Player {
	
	private String name; 
    private int id; // player 1 ή 2
    private String colour; // Blue or Red

    public Player(int id) {
        this.name = "";
        this.id = id;
        this.colour = "";
    }

    public String getName() {	
        return name;
    }

    public String getColour() {	
        return colour;
    }

    public void readName() {
        String choice = (id == 1) ? "Please enter the name of the 1st player:" 
                                  : "Please enter the name of the 2nd player:";

        String input = JOptionPane.showInputDialog(choice);

        if (input == null) {
            JOptionPane.showMessageDialog(null, "Try again");
        }

        name = input.trim();
        if (name.isEmpty()) {
            name = "Player " + id;
        }
    }
    public void readColour(String alreadyChosenColour) { //Settling the  color of the player
        while (true) {
            String input = JOptionPane.showInputDialog(
                name + ", please select your colour (Blue / Red):");

            if (input == null) {
                JOptionPane.showMessageDialog(null, "Try again.");
            }

            input = input.trim();

            if (!input.equalsIgnoreCase("Blue") && !input.equalsIgnoreCase("Red")) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please choose 'Blue' or 'Red'.");
            } else if (alreadyChosenColour != null && input.equalsIgnoreCase(alreadyChosenColour)) {
                JOptionPane.showMessageDialog(null, "That colour is already taken. Please choose the other one.");
            } else {
                this.colour = input.equalsIgnoreCase("Blue") ? "Blue" : "Red";
                break;
            }
        }
    }

    public void setColour(String aColour) {
        this.colour = aColour;
    }

	public void setName(String name) {
		this.name = name;
	}

	
}
