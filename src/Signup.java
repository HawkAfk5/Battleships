import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Signup extends JDialog {

    private ArrayList<User> users;

    public Signup(JFrame parent, ArrayList<User> users) {
        super(parent, "Signup", true);
        this.users = users;

        setSize(350, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel userLabel = new JLabel("New Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("New Password:");
        JPasswordField passField = new JPasswordField();

        JButton signupBtn = new JButton("Signup");
        JButton cancelBtn = new JButton("Cancel");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(signupBtn);
        add(cancelBtn);

        signupBtn.addActionListener(e -> {
            String newUser = userField.getText().trim();
            String newPass = new String(passField.getPassword());

            if (newUser.isEmpty() || newPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
                return;
            }

            boolean exists = false;
            for (User u : users) {
                if (u.getUsername().equals(newUser)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                JOptionPane.showMessageDialog(this, "Username already exists");
            } else {
                users.add(new User(newUser, newPass));
                JOptionPane.showMessageDialog(this, "User created successfully!");
                dispose();
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }
}