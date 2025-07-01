import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;

public class MainFrame extends JFrame {
    private User user;
    private NoteManager noteManager;
    private JTextArea noteArea;
    private JComboBox<String> moodBox;
    private JTextArea allNotesArea;

    public MainFrame(User user) {
        this.user = user;
        this.noteManager = new NoteManager(user.getUsername());

        setTitle("Note2Self – " + user.getUsername());
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        noteArea = new JTextArea(5, 30);
        moodBox = new JComboBox<>(new String[]{"Happy", "Sad", "Angry", "Anxious", "Excited", "Tired"});
        JButton saveButton = new JButton("Save Note");

        saveButton.addActionListener(e -> {
            String mood = (String) moodBox.getSelectedItem();
            String text = noteArea.getText().trim();
            if (!text.isEmpty()) {
                Note note = new Note(LocalDateTime.now().toString(), mood, text);
                noteManager.saveNote(note);
                String quote = noteManager.getQuoteForMood(mood);
                JOptionPane.showMessageDialog(this, "Note saved!\nQuote: " + quote);
                noteArea.setText("");
                displayAllNotes();
            } else {
                JOptionPane.showMessageDialog(this, "Note cannot be empty!");
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Write a Note"));
        inputPanel.add(new JScrollPane(noteArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Mood:"));
        controlPanel.add(moodBox);
        controlPanel.add(saveButton);

        allNotesArea = new JTextArea(15, 50);
        allNotesArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
        add(new JScrollPane(allNotesArea), BorderLayout.SOUTH);

        displayAllNotes();
        setVisible(true);
    }

    private void displayAllNotes() {
        List<Note> notes = noteManager.getAllNotes();
        StringBuilder sb = new StringBuilder("All Notes for " + user.getUsername() + ":\n\n");
        for (Note n : notes) {
            sb.append("[").append(n.getDateTime()).append("] ")
              .append("(").append(n.getMood()).append(")\n")
              .append(n.getText()).append("\n\n");
        }
        allNotesArea.setText(sb.toString());
    }
}
