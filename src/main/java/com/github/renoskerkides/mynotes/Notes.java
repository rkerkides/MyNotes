package com.github.renoskerkides.mynotes;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class Notes {
    private final ArrayList<Note> notes;
    private int currentMaxId;

    public Notes() {
        // Initializing notes by reading from file
        this.notes = NotesFileUtil.readNotes();
        // Determining the maximum ID among the notes
        this.currentMaxId = determineCurrentMaxId();
    }

    private int determineCurrentMaxId() {
        // Using streams to find the maximum ID in the notes list
        return notes.stream()
                .mapToInt(Note::getId)
                .max()
                .orElse(0);
    }

    // Getter for notes
    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void writeNewNote(Scanner scanner) {
        // Asking for note header and content
        System.out.print("\nEnter your note's header: ");
        String header = scanner.nextLine();
        System.out.print("\nEnter your note's content: ");
        String content = scanner.nextLine();

        // Incrementing currentMaxId and using it as the ID for the new note
        int newId = ++currentMaxId;

        // Adding the new note to the list and writing the list to the file
        notes.add(new Note(header, content, newId));
        NotesFileUtil.writeNotes(notes);
    }

    // A method to read notes
    public void readNotes(Scanner scanner) {
        // Checking if there are no notes to read
        if (notes.isEmpty()) {
            System.out.println("\nNo notes to read!");
            return;
        }
        // Displaying the number of notes and asking the user to select a note to read
        System.out.printf("\nYou currently have %d notes stored. " +
                "Please select the note you would like to read.%n", notes.size());

        // Listing the notes
        listNotes();
        String noteToRead = scanner.nextLine();
        displayNote(noteToRead);
    }

    public void deleteNotes(Scanner scanner) {
        // Checking if there are no notes to delete
        if (notes.isEmpty()) {
            System.out.println("\nNo notes to delete!");
            return;
        }
        // Displaying the number of notes and asking the user to select a note to delete
        System.out.printf("\nYou currently have %d notes stored. " +
                "Please select the note you would like to delete.%n", notes.size());

        // Listing the notes
        listNotes();
        String noteToDelete = scanner.nextLine();
        deleteNote(noteToDelete);
    }

    public void updateNotes(Scanner scanner) {
        // Checking if there are no notes to update
        if (notes.isEmpty()) {
            System.out.println("\nNo notes to update!");
            return;
        }
        // Displaying the number of notes and asking the user to select a note to update
        System.out.printf("\nYou currently have %d notes stored. " +
                "Please select the note you would like to update.%n", notes.size());

        // Listing the notes
        listNotes();
        String noteToUpdate = scanner.nextLine();
        updateNote(noteToUpdate, scanner);
    }

    // A method to list notes
    public void listNotes() {
        // Using IntStream to process notes in chunks
        IntStream.range(0, notes.size())
                .filter(i -> i % 5 == 0)
                .forEach(i -> {
                    // Creating a sublist for each chunk and joining the note headers with commas
                    String line = notes.subList(i, Math.min(i + 5, notes.size()))
                            .stream()
                            .map(note -> String.format("(%d) %s", note.getId(), note.getHeading()))
                            .collect(Collectors.joining(", "));

                    // Printing each chunk of note headers
                    System.out.println("\n" + line + "\n");
                });
    }

    public Optional<Note> findNote(String noteToFind) {
        // Handling both ID and header as input for reading a note
        try {
            int noteID = Integer.parseInt(noteToFind);
            Optional<Note> optional = notes.stream()
                    .filter(n -> n.getId() == noteID)
                    .findFirst();
            return optional;
        } catch (NumberFormatException e) {
            Optional<Note> optional = notes.stream()
                    .filter(n -> n.getHeading().equals(noteToFind))
                    .findFirst();
            return optional;
        }
    }

    // A method to display a note
    public void displayNote(String noteToRead) {
        // Use the findNote method to return an optional of the requested Note
        Optional<Note> optional = findNote(noteToRead);

        // Displaying the note if found by header
        if (optional.isPresent()) {
            Note note = optional.get();
            System.out.printf("\n(%d) %s: %s%n", note.getId(), note.getHeading(), note.getContent());
        } else {
            System.out.printf("\nError, note '%s' not found!%n", noteToRead);
        }
    }

    public void deleteNote(String noteToDelete) {
        // Use the findNote method to return an optional of the requested Note
        Optional<Note> optional = findNote(noteToDelete);

        // Delete the note from the notes list
        if (optional.isPresent()) {
            Note note = optional.get();
            notes.remove(note);
            updateIDs();
            NotesFileUtil.writeNotes(notes);
            System.out.println("\nDeleted successfully!");
        } else {
            System.out.printf("\nError, note '%s' not found!%n", noteToDelete);
        }
    }

    public void updateNote(String noteToUpdate, Scanner scanner) {
        // Use the findNote method to return an optional of the requested Note
        Optional<Note> optional = findNote(noteToUpdate);

        // Update the note
        if (optional.isPresent()) {
            Note note = optional.get();
            System.out.printf("\nCurrent heading: %s%n", note.getHeading());
            System.out.println("\nSet new heading (press enter to keep the current heading): ");
            String heading = scanner.nextLine();
            // If the user entered a new heading, update the heading
            if (!heading.trim().isEmpty()) {
                note.setHeading(heading);
            }

            System.out.printf("\nCurrent content: %s%n", note.getContent());
            System.out.println("\nSet new content (press enter to keep the current content): ");
            String content = scanner.nextLine();
            // If the user entered new content, update the content
            if (!content.trim().isEmpty()) {
                note.setContent(content);
            }
            NotesFileUtil.writeNotes(notes);
            System.out.println("\nUpdated successfully!");
        } else {
            System.out.printf("\nError, note '%s' not found!%n", noteToUpdate);
        }
    }


    // Update IDs to ensure that they are continuous following note deletion
    public void updateIDs() {
        for (int i = 0; i < notes.size(); i++) {
            notes.get(i).setId(i + 1); // Set ID starting from 1
        }
        // Update the currentMaxId to reflect the last ID in the list
        currentMaxId = notes.size();
    }
}
