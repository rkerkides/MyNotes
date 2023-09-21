package com.github.renoskerkides.mynotes;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Notes notes = new Notes();

        System.out.println("\nWelcome to MyNotes!");

        while (true) {
            System.out.println("\nPlease choose an operation.");
            System.out.println("1. Write a new note");
            System.out.println("2. Read existing notes");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1/2/3): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please select 1, 2, or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    notes.writeNewNote(scanner);
                    break;
                case 2:
                    notes.readNotes(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }
}