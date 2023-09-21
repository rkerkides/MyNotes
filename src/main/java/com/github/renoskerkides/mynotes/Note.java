package com.github.renoskerkides.mynotes;

public class Note {
    private String heading;
    private String content;

    public Note(String heading, String content) {
        this.heading = heading;
        this.content = content;
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

    public void setContent(String content) {
        this.content = content;
    }
}
