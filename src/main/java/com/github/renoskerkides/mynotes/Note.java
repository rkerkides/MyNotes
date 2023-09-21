package com.github.renoskerkides.mynotes;

public class Note {
    private String heading;
    private String content;
    private int id;

    public Note(String heading, String content, int id) {
        this.heading = heading;
        this.content = content;
        this.id = id;
    }

    public Note() {
        // default constructor needed for Jackson
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
