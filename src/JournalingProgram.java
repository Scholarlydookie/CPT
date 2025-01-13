import java.util.Scanner;

public class JournalingProgram {
    public static Scanner input;

    public static void initScanner() {
        input = new Scanner(System.in);
    }

    public static void clearScreen() {   
        System.out.print("\033[H\033[2J");   
        System.out.flush();   
    }

    public static void main(String[] args) {
        boolean running = true;
        initScanner();

        while (running) {
            clearScreen();
            System.out.println("|----------------------------------|");
            System.out.println("|Welcome to the Journaling Program!|");
            System.out.println("|----------------------------------|");
            System.out.println("|1. Note Taking                    |");
            System.out.println("|2. Calendar Organization          |");
            System.out.println("|3. Media                          |");
            System.out.println("|4. Exit                           |");
            System.out.println("|----------------------------------|");
            System.out.print("|Input:                            |");
            String b = "\b".repeat(28);
            System.out.print(b);

            if (input.hasNextInt()) {
                int pick = input.nextInt();
                switch (pick) {
                    case 1:
                        clearScreen();
                        noteTaking();
                        break;
                    case 2:
                        clearScreen();
                        calendarOrganization();
                        break;
                    case 3:
                        clearScreen();
                        media();
                        break;
                    case 4:
                        running = false;
                        clearScreen();
                        System.out.println("Exiting Program...");
                        break;
                    default:
                        clearScreen();
                        System.out.println("!!!!Invalid Input, Try again!!!!");
                        break;
                }
            } else {
                clearScreen();
                System.out.println("!!!!Invalid Input, Try again!!!!");
                input.next(); // Clear the invalid input
            }
        }
    }

    public static void noteTaking() {
        // Note-taking logic
        Note.main(new String[]{});
    }

    public static void calendarOrganization() {
        // Calendar organization logic
        Calendar.main(new String[]{});
    }

    public static void media() {
        // Media logic
        Media.main(new String[]{});
    }
}
