package com.github.renoskerkides.mynotes;

public class Note {
    // Instance variables to store the heading, content, and id of a note
    private String heading;
    private String content;
    private int id;

    // Constructor to initialize a new note with heading, content, and id
    public Note(String heading, String content, int id) {
        this.heading = heading;
        this.content = content;
        this.id = id;
    }

    // Default constructor needed for the Jackson library to deserialize JSON into Note objects
    public Note() {
        // default constructor needed for Jackson
    }

    // Getter method to retrieve the heading of the note
    public String getHeading() {
        return heading;
    }

    // Setter method to update the heading of the note
    public void setHeading(String heading) {
        this.heading = heading;
    }

    // Getter method to retrieve the content of the note
    public String getContent() {
        return content;
    }

    // Getter method to retrieve the id of the note
    public int getId() {
        return id;
    }

    // Setter method to update the id of the note
    public void setId(int id) {
        this.id = id;
    }

    // Setter method to update the content of the note
    public void setContent(String content) {
        this.content = content;
    }
}
