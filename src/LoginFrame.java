import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthManager authManager;

    public LoginFrame() {
        setTitle("Login to Note2Self");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        authManager = new AuthManager();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (authManager.authenticate(username, password)) {
                dispose();
                new MainFrame(new User(username));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login credentials.");
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (authManager.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful. Now login.");
            } else {
                JOptionPane.showMessageDialog(this, "User already exists.");
            }
        });

        setVisible(true);
    }
}
