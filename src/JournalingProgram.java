import java.util.Scanner;

public class JournalingProgram {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("|----------------------------------|");
        System.out.println("|Welcome to the Journaling Program!|");
        System.out.println("|----------------------------------|");
        System.out.println("|1. Note Taking                    |");
        System.out.println("|2. Calendar Organization          |");
        System.out.println("|3. Media Logging                  |");
        System.out.println("|4. Exit                           |");
        System.out.println("|----------------------------------|");
        System.out.print("|Input:                            |");  
        System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");  // Move cursor back for input
        int choice = input.nextInt();
    }
}


