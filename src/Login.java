import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Login extends JDialog {

    private ArrayList<User> users;

    public Login(JFrame parent, ArrayList<User> users) {
        super(parent, "Login", true);
        this.users = users;

        setSize(350, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginBtn);
        add(cancelBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());

            boolean found = false;
            for (User u : users) {
                if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                parent.setVisible(false);
                new StartingScreenGUI(users);
                JOptionPane.showMessageDialog(this, "Welcome, " + user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }
}