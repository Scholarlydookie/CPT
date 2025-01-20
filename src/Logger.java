import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Logger {

private static final String LOGGER_DIRECTORY = "logger"; // Directory for logged media
private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // Date format

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("|----------------------------------|");
            System.out.println("|Logger                           |");
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
                    viewEntries(LOGGER_DIRECTORY);
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
        String date = LocalDate.now().format(DATE_FORMATTER);

        System.out.print("Enter the title: ");
        String title = Main.input.nextLine();

        System.out.print("Enter the creator/director: ");
        String creator = Main.input.nextLine();

        System.out.print("Enter any extra comments: ");
        String comments = Main.input.nextLine();

        File directory = new File(LOGGER_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(new File(directory, title + ".txt"))) {
            writer.write(date + System.lineSeparator());
            writer.write(title + System.lineSeparator());
            writer.write(creator + System.lineSeparator());
            writer.write(comments);
            System.out.println("Entry has been logged.");
        } catch (IOException e) {
            System.out.println("An error occurred while logging the entry.");
        }
    }

    public static void viewEntries(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || Objects.requireNonNull(directory.listFiles()).length == 0) {
            System.out.println("!!!No entries available!!!");
            return;
        }

        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                System.out.println("|----------------------------------|");
                System.out.println(reader.lines().reduce("", (a, b) -> a + System.lineSeparator() + b));
                System.out.println("|----------------------------------|");
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.getName());
            }
        }
    }
}

