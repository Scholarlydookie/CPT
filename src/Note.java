import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader;

public class Note {

    public static void view(){
        JournalingProgram.initScanner();

        File directory = new File("txt"); //creates directory called "txt"
        if (!directory.exists()) //if its already there just say no notes avalible!
        {System.out.println("No notes available.");  
    }

        File[] files = directory.listFiles(); //uses an array to list the files
        if (files == null || files.length == 0) { //if the length is 0 or it gets back null it just assumess theres no notes
            System.out.println("No notes available.");
            return;
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            //TODO figure out what bufferedReader even is
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) { 
                String date = reader.readLine(); // Read the date
                String name = reader.readLine(); // Read the name
                System.out.println((i + 1) + ". " + name + " (Date: " + date + ")");} //print the number,name, and date
           
                catch (Exception error) { System.out.println("An error occurred while reading the note.");} 
            }

        System.out.print("Enter the number of the note to view: ");
        int choice = JournalingProgram.input.nextInt();
        
        if (choice > 0 && choice <= files.length) { 
            try (BufferedReader reader = new BufferedReader(new FileReader(files[choice - 1]))) {
                //were now reading the text file
                reader.readLine(); // Skip the first line (date)
                reader.readLine(); // Skip the second line (name)
            // Read all remaining lines as the content of the note
            String noteContent = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("Note Content: " + noteContent);


    } catch (Exception error) {System.out.println("An error occurred while reading the note.");}
        }      
    else {System.out.println("Invalid choice.");}
    }


    public static void Create() {
        JournalingProgram.initScanner();

        LocalDate date = LocalDate.now(); //checks date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); 
        String formattedDate = date.format(formatter); //reformatting date for my own convenience

        System.out.print("Enter the name of your note: ");
        String noteName = JournalingProgram.input.nextLine(); //note name input

        System.out.print("Enter your note: ");
        String note = JournalingProgram.input.nextLine(); //actual note

        File directory = new File("txt");//this is checking for /txt
        if (!directory.exists()) {directory.mkdirs();} //mkdirs is "make directories"

        try {
            FileWriter writer = new FileWriter("txt/" + noteName + ".txt", true); //write a new file
            writer.write(formattedDate + "\n"); //first line is the date
            writer.write(noteName + "\n"); //second line is the name of the note
            writer.write(note + "\n"); // and the third line is the actual note
            writer.close(); //ends writing function
            System.out.println("Note has been saved.");

        } catch (Exception error) { // Catching general Exception
            System.out.println("An error occurred while saving the note.");
        }
        
    }

    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            JournalingProgram.initScanner();
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

            String pick = JournalingProgram.input.nextLine();

            switch (pick) { //option checker
                case "A":
                case "a":
                    JournalingProgram.clearScreen();
                    view(); //view notes
                    break; 

                case "B":
                case "b":
                    JournalingProgram.clearScreen();
                    Create(); //create new note
                    break;

                case "C":
                case "c":
                    running = false; // exit loop and return to main menu
                    JournalingProgram.clearScreen();
                    JournalingProgram.main(new String[]{}); //go back to main menu
                    break;

                default:
                    JournalingProgram.clearScreen();
                    System.out.println("!!!!Invalid Input, Try again!!!!");
                    break;
            }
        }
    }
}
