package com.github.renoskerkides.mynotes;
import java.util.Scanner;

public class Main {

    // Main method to run the application
    public static void main(String[] args) {
        // Creating a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Creating a Notes object to perform operations on notes
        Notes notes = new Notes();

        // Welcome message
        System.out.println("\nWelcome to MyNotes!");

        // Infinite loop to keep presenting menu to the user
        while (true) {
            // Printing the menu for the user
            System.out.println("\nPlease choose an operation.");
            System.out.println("1. Write a new note");
            System.out.println("2. Read existing notes");
            System.out.println("3. Delete an existing note");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");

            int choice;
            try {
                // Trying to parse user input as an integer
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Handling invalid input
                System.out.println("Invalid input. Please select 1, 2, 3, or 4.");
                continue;
            }

            // Switch statement to handle user choices
            switch (choice) {
                case 1:
                    // Writing a new note
                    notes.writeNewNote(scanner);
                    break;
                case 2:
                    // Reading existing notes
                    notes.readNotes(scanner);
                    break;
                case 3:
                    // Delete existing note
                    notes.deleteNotes(scanner);
                    break;
                case 4:
                    // Exiting the program
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    // Handling invalid choice
                    System.out.println("Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }
    }
}
