import java.io.*;
import java.nio.file.*;
import java.util.*;

public class NoteManager {
    private List<Note> notes;
    private String noteFile;
    private static final String QUOTE_FILE = "quotes.txt";

    public NoteManager(String username) {
        noteFile = "notes_" + username + ".txt";
        notes = new ArrayList<>();
        loadNotes();
    }

    public void saveNote(Note note) {
        notes.add(note);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(noteFile, true))) {
            writer.write(note.toFileString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Note> getAllNotes() {
        return notes;
    }

    private void loadNotes() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(noteFile));
            for (String line : lines) {
                Note n = Note.fromFileString(line);
                if (n != null) notes.add(n);
            }
        } catch (IOException e) {
            // File not found initially is fine
        }
    }

    public String getQuoteForMood(String mood) {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUOTE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().startsWith(mood.toLowerCase() + ":")) {
                    return line.split(":", 2)[1].trim();
                }
            }
        } catch (IOException e) {
            return "No quote found.";
        }
        return "No quote for this mood.";
    }
}
