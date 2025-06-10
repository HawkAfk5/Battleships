import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	//Creation of GUI
	        	ArrayList<User> users = new ArrayList<>();
	            PreGameWindow preGameWindow = new PreGameWindow(users);
	        }
		 });
	}
}
