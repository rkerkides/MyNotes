package com.github.renoskerkides.mynotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class NotesFileUtil {

    private static final String FILENAME = "notes.json";

    /**
     * Reads notes from a JSON file and returns them as a list of Note objects.
     *
     * @return List of notes. Returns an empty list if there are no notes or if there's an error.
     */
    public static ArrayList<Note> readNotes() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read notes from the specified file and map them to a list of Note objects.
            File file = new File(FILENAME);
            return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Note.class));

        } catch (IOException e) {
            // In case of any issues, return an empty list.
            return new ArrayList<>();
        }
    }

    /**
     * Writes a list of notes to a JSON file.
     *
     * @param notes List of notes to be written.
     */
    public static void writeNotes(ArrayList<Note> notes) {
        ObjectMapper mapper = new ObjectMapper();

        // If notes exist, ensure unique ID assignment by incrementing the highest existing ID.
        if (!notes.isEmpty()) {
            AtomicInteger maxId = new AtomicInteger(notes.stream().max(Comparator.comparingInt(Note::getId)).get().getId());
            notes.forEach(note -> {
                if (note.getId() == 0) {
                    maxId.getAndIncrement();
                    note.setId(maxId.get());
                }
            });
        }

        try {
            // Write the notes to the specified file.
            mapper.writeValue(new File(FILENAME), notes);
            System.out.println("\nNote added successfully!");
        } catch (IOException e) {
            System.out.println("\nError writing to file: " + e.getMessage());
        }
    }
}
