public class Note {
    private String dateTime;
    private String mood;
    private String text;

    public Note(String dateTime, String mood, String text) {
        this.dateTime = dateTime;
        this.mood = mood;
        this.text = text;
    }

    public String getDateTime() { return dateTime; }
    public String getMood() { return mood; }
    public String getText() { return text; }

    public String toFileString() {
        return dateTime + "::" + mood + "::" + text.replace("\n", "\\n");
    }

    public static Note fromFileString(String line) {
        String[] parts = line.split("::");
        if (parts.length != 3) return null;
        return new Note(parts[0], parts[1], parts[2].replace("\\n", "\n"));
    }
}