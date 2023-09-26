package com.github.renoskerkides.mynotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class NotesFileUtil {

    private static final String FILENAME = "notes.json";

    // Method to read notes from a JSON file
    public static ArrayList<Note> readNotes() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Reading notes from file and mapping them to a list of Note objects
            File file = new File(FILENAME);
            return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Note.class));
        } catch (IOException e) {
            // Returning an empty list in case of error
            return new ArrayList<>();
        }
    }

    // Method to write notes to a JSON file
    public static void writeNotes(ArrayList<Note> notes) {
        ObjectMapper mapper = new ObjectMapper();
        if (!notes.isEmpty()) {
            AtomicInteger maxId = new AtomicInteger(notes.stream().max(Comparator.comparingInt(Note::getId)).get().getId());
            notes.forEach(note -> {
                // Ensuring unique ID assignment by incrementing the highest existing ID
                if (note.getId() == 0) {
                    maxId.getAndIncrement();
                    note.setId(maxId.get());
                }
            });
        }

        try {
            // Writing notes to file
            mapper.writeValue(new File(FILENAME), notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
