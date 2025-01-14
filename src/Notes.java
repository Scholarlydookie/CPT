import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Notes {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean run = true;

        while (run) {
            System.out.println("|----------------------------------|");
            System.out.println("|What will you do today?           |");
            System.out.println("|----------------------------------|");
            System.out.println("|A. View Notes                     |");
            System.out.println("|B. Write Note                     |");
            System.out.println("|C. Return to main menu            |");
            System.out.println("|----------------------------------|");
            System.out.print("|Input:                            |");
            String b = "\b".repeat(28);
            System.out.print(b);

            String pick = input.nextLine().toLowerCase();

            switch (pick) {
                case "a":
                    Main.clearScreen();
                    viewNotes();
                    break;
                case "b":
                    Main.clearScreen();
                    createNote();
                    break;
                case "c":
                    run = false; // exit loop and return to main menu
                    Main.clearScreen();
                    Main.main(new String[]{});
                    break;
                default:
                    Main.clearScreen();
                    System.out.println("!!!!Invalid Input, Try again!!!!");
                    break;
            }
        }
    }

    private static void viewNotes() {
        File directory = new File("txt");
        if (!directory.exists() || directory.listFiles().length == 0) {
            System.out.println("No notes available.");
            return;
        }

        List<NoteData> notes = loadNotes(directory);
        displayNotes(notes);
        
        System.out.print("Enter the number of the note to view: ");
        int choice = input.nextInt();
        input.nextLine();  // consume the newline character

        if (choice > 0 && choice <= notes.size()) {
            System.out.println("Note Content: " + notes.get(choice - 1).content);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static List<NoteData> loadNotes(File directory) {
        List<NoteData> notes = new ArrayList<>();
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String date = reader.readLine();
                    String name = reader.readLine();
                    String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                    notes.add(new NoteData(date, name, content));
                } catch (IOException e) {
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }
        return notes;
    }

    private static void displayNotes(List<NoteData> notes) 
    {for (int i = 0; i < notes.size(); i++)
        {NoteData note = notes.get(i); 
        System.out.println((i + 1) + ". " + note.name + " (Date: " + note.date + ")");}
        }

    private static void createNote() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = date.format(formatter);

        System.out.print("Enter the name of your note: ");
        String noteName = input.nextLine();

        System.out.print("Enter your note: ");
        String noteContent = input.nextLine();

        File directory = new File("txt");
        if (!directory.exists()) { directory.mkdirs();}

        try (FileWriter writer = new FileWriter("txt/" + noteName + ".txt", true)) {
            writer.write(formattedDate + "\n");
            writer.write(noteName + "\n");
            writer.write(noteContent + "\n");
            System.out.println("Note has been saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the note.");
        }
    }

    // A simple class to represent note data (date, name, content)
    static class NoteData {
        String date;
        String name;
        String content;

        NoteData(String date, String name, String content) {
            this.date = date;
            this.name = name;
            this.content = content;
        }
    }
}
