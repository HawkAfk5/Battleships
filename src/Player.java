import java.util.Scanner;
//κλάση για τον παίχτη
public class Player {
	//ιδιότητες
	private String name; //όνομα
	private int id; //παίχτης 1 ή 2
	private char chip; //μάρκα
	
	//μέθοδοι
	public Player(int id) //κατασκευαστής
	{
		name = ""; //αρχικοποιούμε name και chip 
		this.id = id;
		chip = ' ';
	}
	
	//getters
	public String getName() //μέθοδος πρόσβασης στο όνομα
	{
		return name;
	}
	
	public char getChip() //μέθοδος πρόσβασης στη μάρκα
	{
		return chip;
	}
	
	//setters
	public void readName() //μέθοδος διαβάσματος ονόματος και για τους δυο παίχτες
	{
		Scanner in = new Scanner(System.in); //για το διάβασμα από τον χρήστη
		if(id == 1) System.out.print("Please enter the name of the 1st player: "); //προτροπή για τον πρώτο παίχτη 
		else System.out.print("Please enter the name of the 2nd player: "); //προτροπή για τον δεύτερο παίχτη 
		name = in.nextLine(); //διάβασμα
	}
	
	public void readChip() //μέθοδος διαβάσματος μάρκας για τον πρώτο παίχτη
	{
		Scanner in = new Scanner(System.in); //για το διάβασμα από τον χρήστη
		while(true) //while loop μέχρι να εισάγει ο χρήστης δεκτό input
		{
			System.out.print(name + ", please select your chip: "); //προτροπή
			chip = in.next().charAt(0); //διάβασμα
			if(chip=='x' || chip=='o') break; //δεκτό input
			else System.out.print("Incorrect input."); //μη δεκτό input
		}
	}
	
	public void setChip(char aChip) //μέθοδος ορισμού μάρκας για τον δεύτερο παίχτη
	{
		chip = aChip;
	}
}
