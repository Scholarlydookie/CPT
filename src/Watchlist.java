import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Watchlist {
    
    private static final String WATCHLIST_DIRECTORY = "watchlist"; // Directory for watchlist entries
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("|----------------------------------|");
            System.out.println("|Watchlist                        |");
            System.out.println("|----------------------------------|");
            System.out.println("|A. Add Entry                     |");
            System.out.println("|B. View Entries                  |");
            System.out.println("|C. Return to Main Menu           |");
            System.out.println("|----------------------------------|");
            Main.GetBack();

            String choice = Main.input.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    Main.clearScreen();
                    addEntry();
                    break;
                case "b":
                    Main.clearScreen();
                    Logger.viewEntries(WATCHLIST_DIRECTORY);
                    break;
                case "c":
                    running = false;
                    Main.clearScreen();
                    break;
                default:
                    Main.clearScreen();
                    System.out.println("!!!Invalid input. Please try again.!!!");
            }
        }
    }

    private static void addEntry() {
        System.out.print("Enter the title: ");
        String title = Main.input.nextLine();

        System.out.print("Enter the creator/director: ");
        String creator = Main.input.nextLine();

        System.out.print("Enter any extra comments: ");
        String comments = Main.input.nextLine();

        File directory = new File(WATCHLIST_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(new File(directory, title + ".txt"))) {
            writer.write(title + System.lineSeparator());
            writer.write(creator + System.lineSeparator());
            writer.write(comments);
            System.out.println("Entry has been added to the watchlist.");
        } catch (IOException e) {
            System.out.println("An error occurred while adding to the watchlist.");
        }
    }
}

