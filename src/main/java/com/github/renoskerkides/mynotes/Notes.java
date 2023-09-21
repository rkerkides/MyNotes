package com.github.renoskerkides.mynotes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Notes {
    private final ArrayList<Note> notes;
    private int currentMaxId;

    public Notes() {
        this.notes = NotesFileUtil.readNotes();
        this.currentMaxId = determineCurrentMaxId();
    }

    private int determineCurrentMaxId() {
        return notes.stream()
                .mapToInt(Note::getId)
                .max()
                .orElse(0);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void writeNewNote(Scanner scanner) {
        System.out.print("\nEnter your note's header: ");
        String header = scanner.nextLine();
        System.out.print("\nEnter your note's content: ");
        String content = scanner.nextLine();
        int newId = ++currentMaxId;  // Use the currentMaxId for ID generation
        notes.add(new Note(header, content, newId));
        NotesFileUtil.writeNotes(notes);
    }

    public void readNotes(Scanner scanner) {
        if (notes.isEmpty()) {
            System.out.println("\nNo notes to read!");
            return;
        }
        System.out.printf("\nYou currently have %d notes stored. " +
                "Please select the note you would like to read.%n", notes.size());
        listNotes();
        String noteToRead = scanner.nextLine();
        displayNote(noteToRead);
    }

    /**
     * Lists the header names from the notes. For display purposes,
     * this method organizes the header names into groups of 5 (or less for the final group),
     * and prints each group on a single line separated by commas.
     */
    public void listNotes() {
        // Using an IntStream to process every 5th index for chunking
        IntStream.range(0, notes.size()) // Create a stream of indices
                .filter(i -> i % 5 == 0)  // Filter to get every 5th index
                .forEach(i -> {
                    // Create a sublist for the current chunk of 5 headers (or less for the final chunk)
                    String line = notes.subList(i, Math.min(i + 5, notes.size()))
                            .stream()
                            .map(note -> String.format("(%d) %s", note.getId(), note.getHeading()))
                            .collect(Collectors.joining(", "));  // Join the notes with commas

                    System.out.println("\n" + line + "\n");  // Print the joined notes
                });
    }


    public void displayNote(String noteToRead) {
        try {
            int noteID = Integer.parseInt(noteToRead);
            Optional<Note> optional = notes.stream()
                    .filter(n -> n.getId() == (noteID))
                    .findFirst();

            if (optional.isPresent()) {
                Note note = optional.get();
                System.out.printf("\n(%d) %s: %s", note.getId(), note.getHeading(), note.getContent());
            } else {
                System.out.printf("\nError, note '%s' not found!%n", noteToRead);
            }
        } catch (NumberFormatException e) {
            Optional<Note> optional = notes.stream()
                    .filter(n -> n.getHeading().equals(noteToRead))
                    .findFirst();

            if (optional.isPresent()) {
                Note note = optional.get();
                System.out.printf("\n(%d) %s: %s", note.getId(), note.getHeading(), note.getContent());
            } else {
                System.out.printf("\nError, note '%s' not found!%n", noteToRead);
            }
        }
    }
}
