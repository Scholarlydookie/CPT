import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
 * Karam Mahmoud
 * 2025/01/19
 * Media.java, The main class for the media logging part of this
 */

/**
 * The {@code Media} class provides functionality to log and manage media entries (Movies and Shows).
 * It supports two features: Logger and Watchlist.
 */
public class Media {

    /**
     * Text-based menu for the media manager.
     */
    private static void displayMenu() {
        System.out.println("|----------------------------------|");
        System.out.println("|Media Manager                     |");
        System.out.println("|----------------------------------|");
        System.out.println("|A. Logger                         |");
        System.out.println("|B. Watchlist                      |");
        System.out.println("|C. Exit                           |");
        System.out.println("|----------------------------------|");
        Main.GetBack();
    }

    public static void main(String[] args) {
        boolean running = true;
        Main.initScanner();

        while (running) {
            displayMenu();
            String choice = Main.input.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    Main.clearScreen();
                    Logger.main(args);
                    break;
                case "b":
                    Main.clearScreen();
                    Watchlist.main(args);
                    break;
                case "c":
                    running = false;
                    Main.clearScreen();
                    System.out.println("Exiting Media Manager. Goodbye!");
                    Main.wait(1000);
                    break;
                default:
                    Main.clearScreen();
                    System.out.println("!!!Invalid input. Please try again.!!!");
            }
        }
        Main.input.close();
    }
}