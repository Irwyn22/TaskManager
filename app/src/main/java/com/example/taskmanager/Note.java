package com.example.taskmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int noteId;

    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_content")
    private String noteContent;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    public Note(String noteTitle, String noteContent, String createdAt, int categoryId) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
    }

    public int getNoteId() { return noteId; }
    public void setNoteId(int noteId) { this.noteId = noteId; }

    public String getNoteTitle() { return noteTitle; }
    public String getNoteContent() { return noteContent; }
    public String getCreatedAt() { return createdAt; }
    public int getCategoryId() { return categoryId; }
}



