package com.udacity.jwdnd.course1.cloudstorage.Forms;

import lombok.Data;

@Data
public class NoteForm {
    private String title;
    private String description;

    // Generate NoteForm constuctor
    public NoteForm(String title, String description, String noteId) {
        this.title = title;
        this.description = description;
        this.noteId = noteId;
    }
    // Generate NoteForm Setter and Getter methods
    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    private String noteId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
