package com.example.taskmanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Query("SELECT * FROM notes WHERE category_id = :categoryId")
    List<Note> getNotesByCategory(int categoryId);

    @Query("SELECT * FROM notes WHERE note_title LIKE '%' || :text || '%'")
    List<Note> searchNotes(String text);
}

