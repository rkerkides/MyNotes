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
    private ArrayList<Note> notes;

    public Notes() {
        this.notes = NotesFileUtil.readNotes();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void writeNewNote(Scanner scanner) {
        System.out.print("\nEnter your note's header: ");
        String header = scanner.nextLine();
        System.out.print("\nEnter your note's content: ");
        String content = scanner.nextLine();
        notes.add(new Note(header, content));
        NotesFileUtil.writeNotes(notes);
    }

    public void readNotes(Scanner scanner) {
        if (notes.isEmpty()) {
            System.out.println("\nNo notes to read!");
            return;
        }
        System.out.printf("\nYou currently have %d notes stored. " +
                "Please select the note you would like to read.%n", notes.size());
        listHeaderNames();
        String noteToRead = scanner.nextLine();
        displayNote(noteToRead);
    }

    /**
     * Lists the header names from the notes. For display purposes,
     * this method organizes the header names into groups of 5 (or less for the final group),
     * and prints each group on a single line separated by commas.
     */
    public void listHeaderNames() {
        // Extract the headers from each note
        List<String> headers = notes.stream()
                .map(Note::getHeading)    // Map each note to its heading
                .toList();                // Collect results into a List

        // Using an IntStream to process every 5th index for chunking
        IntStream.range(0, headers.size())    // Create a stream of indices
                .filter(i -> i % 5 == 0)      // Filter to get every 5th index
                .forEach(i -> {
                    // Create a sublist for the current chunk of 5 headers (or less for the final chunk)
                    String line = headers.subList(i, Math.min(i + 5, headers.size()))
                            .stream()
                            .collect(Collectors.joining(", "));  // Join the headers with commas

                    System.out.println("\n" + line + "\n");  // Print the joined headers
                });
    }

    public void displayNote(String noteToRead) {
        Optional<Note> note = notes.stream()
                .filter(n -> n.getHeading().equals(noteToRead))
                .findFirst();

        if (note.isPresent()) {
            System.out.println("\n" + note.get().getHeading()
                    + ": " + note.get().getContent());
        } else {
            System.out.printf("\nError, note '%s' not found!%n", noteToRead);
        }
    }

}
