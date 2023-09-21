package com.github.renoskerkides.mynotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NotesFileUtil {

    private static final String FILENAME = "notes.json";

    public static ArrayList<Note> readNotes() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(FILENAME);
            return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Note.class));

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeNotes(ArrayList<Note> notes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(FILENAME), notes);
            System.out.println("\nNote added successfully!");
        } catch (IOException e) {
            System.out.println("\nError writing to file: " + e.getMessage());
        }
    }
}
