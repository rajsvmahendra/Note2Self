import java.io.*;

public class AuthManager {
    private static final String FILE = "users.txt";

    public boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Ignore for now
        }
        return false;
    }

    public boolean register(String username, String password) {
        if (authenticate(username, password)) return false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            // Ignore for now
        }
        return true;
    }
}
