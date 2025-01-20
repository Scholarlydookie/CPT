import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
 * Karam Mahmoud
 * 2025/01/19
 * Notes.java, One of the files for the jorunaling app
 */

/**
 * The {@code Notes} class lets you manage and create notes.
 * It uses a text file-based storage system located in the {@code txt} directory.
 */
public class Notes {

private static final String NOTES_DIRECTORY = "txt"; //The directory for the notes so i dont rewrite it multiple times
private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd"); //The date formated to my liking
 /**
     * text based menu for the notes manager
     */
private static void displayMenu() { //text menu
    System.out.println("|----------------------------------|");
        System.out.println("|What will you do today?           |");
        System.out.println("|----------------------------------|");
        System.out.println("|A. View Notes                     |");
        System.out.println("|B. Write Note                     |");
        System.out.println("|C. Return to main menu            |");
        System.out.println("|----------------------------------|");
        Main.GetBack();
}

/**
     * The main method that runs the actual program, and gives the user the choice between viewing notes,
     * creating a new note, or returning to the journals main menu.
     *
     * @param args String
     */
public static void main(String[] args) { //choice system
    boolean running = true;
    Main.initScanner(); 

    while (running) {
        displayMenu();
        String choice = Main.input.nextLine().toLowerCase(); // Use Main.input

        switch (choice) {
            case "a":
                Main.clearScreen();
                viewNotes();
                break;
            case "b":
                Main.clearScreen();
                createNote();
                break;
            case "c":
                running = false;
                Main.clearScreen();
                System.out.println("|----------------------------------|");
                System.out.println("|Exiting Notes Manager. Goodbye!   |");
                System.out.println("|----------------------------------|");
                Main.wait(1000);
                Main.clearScreen();
                System.out.println("|----------------------------------|");
                System.out.println("|Loading Main Menu...              |");
                System.out.println("|----------------------------------|");
                Main.wait(1000);
                Main.clearScreen();
                Main.Mainia(); //runs Main.java 
                break;
            default:
                Main.clearScreen();
                System.out.println("!!!Invalid input. Please try again.!!!");
        }
    }
    Main.input.close(); // Close the Scanner at the end
}
 
/**
     * Prompts the user to enter the name of a note and content for a new note, then saves it to the {@code txt} directory.
     */
private static void createNote() { //we need to make a note first off...
    String date = LocalDate.now().format(DATE_FORMATTER);  //computers date with my formating

    System.out.print("Enter the name of your note: ");
    String name = Main.input.nextLine();

    System.out.print("Enter your note: ");
    String content = Main.input.nextLine();

    File directory = new File(NOTES_DIRECTORY);
    if (!directory.exists()) { //"if directory does not exist"
        directory.mkdirs(); //"make it!"
    }

    try (FileWriter writer = new FileWriter(new File(directory, name + ".txt"))) {
        writer.write(date + System.lineSeparator()); //date, new line
        writer.write(name + System.lineSeparator()); //name, new line
        writer.write(content); // and the rest of the note (content)
        System.out.println("Note has been saved."); //huzzah!
    } catch (IOException e) {
        System.out.println("An error occurred while saving the note."); //oops
    }
}

/**
     * Displays a list of existing notes and allows the user to view the content of the note.
     */
 private static void viewNotes() { //now lets actually see these notes!

        File directory = new File(NOTES_DIRECTORY); //lets check the directory
        if (!directory.exists() || Objects.requireNonNull(directory.listFiles()).length == 0) { //1st check if it exists, 2nd check if it has any files and that theyre not null. it does this by making it into a list "directory.listFiles"
            System.out.println("!!!No notes available!!!"); //bummer, go write something
            return;
        }

        System.out.println("|----------------------------------|");
        System.out.println("|What do you want to view?         |");
        System.out.println("|----------------------------------|");

        List<Note> notes = loadNotes(directory); // list of notes using encapsulation, also a toString() method
        displayNotes(notes); //and then displaying them
        System.out.println("|----------------------------------|");

        System.out.println("|Enter the Note # (or 0 to return) |");
        System.out.println("|----------------------------------|");
        Main.GetBack();

    int choice = 0;
    boolean validInput = false;

while (!validInput) {
    if (Main.input.hasNextInt()) {
        choice = Main.input.nextInt();
        Main.input.nextLine(); // Consume the newline character
        validInput = true;
        Main.clearScreen();
        displayNoteContent(notes.get(choice - 1)); //obvious... (run displayNoteContent method and -1 so its the right one on the list)
    } else {
        Main.input.nextLine(); // Consume the invalid input
        Main.clearScreen();
        System.out.println("|----------------------------------|");
        System.out.println("|!!!Invalid input!!!               |"); //error message!!
        viewNotes(); //reruns the program
        }
    }

}

/**
 * Displays the content and lets the user exit the note view or return to the notes list.
 *
 * @param note The note whose content is to be displayed.
 */
private static void displayNoteContent(Note note) { //The notes personal menu (fancy...)
        boolean inNote = true; //this is checking if your actually IN the note file

        while (inNote) { // a bunch of random stuff so it looks cool and old and dumb and cool
            Main.clearScreen();
            System.out.println("|----------------------------------|");
            System.out.println("Displaying " + note.getName() + "...");
            System.out.println("|----------------------------------|");
            Main.wait(1000);
            Main.clearScreen();
            System.out.println(note);

            System.out.println("|----------------------------------|");

            System.out.println("|1. Return to Notes List           |");
            System.out.println("|2. Exit Note                      |");
            System.out.println("|----------------------------------|");
            Main.GetBack();
            
            String choice = Main.input.nextLine();
            switch (choice) {
                case "1":
                Main.clearScreen();
                    return; // Exit the method to return to the notes list
                case "2":
                    Main.clearScreen();
                    inNote = false; // Exit the note view loop
                    break;
                default:
                    Main.clearScreen();
                    System.out.println("Invalid choice. Please try again. (reloading Note)");
                    Main.wait(1000);
                    
        }
    }
}

private static List<Note> loadNotes(File directory) { //making the ACTUAL list
        List<Note> notes = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) { //reading the file
                    String date = reader.readLine();  //set line 1 as String Date
                    String name = reader.readLine(); //set line 2 as String Name
                    String content = reader.lines().reduce("", (a, b) -> a + System.lineSeparator() + b).strip(); //set the rest as String Content and gets rid of any extra spaces
                    notes.add(new Note(date, name, content)); //now add all of it to a new note 
                } catch (IOException e) { //blah blah (file expection checker i found how to do online and implemented using AI)
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }
        return notes;
    }

/**
 * Setting up the notes to be displayed in a list format.
 */
    private static void displayNotes(List<Note> notes) { //formating the note display string
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            System.out.println((i + 1) + ". " + note.getName() + " (Date: " + note.getDate() + ")");
        }
    }

    // Note class with encapsulation and toString()
    private static class Note {
        private final String date;
        private final String name;
        private final String content;

        public Note(String date, String name, String content) {
            this.date = date;
            this.name = name;
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Name: " + name + "\nDate: " + date + "\nContent:\n" + content;
        }
    }
}