package com.example.taskmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView recyclerNotes;
    private NoteAdapter adapter;
    private AppDatabase db;
    private int categoryId;
    private Button btnAddNote;
    private EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        recyclerNotes = findViewById(R.id.recyclerNotes);
        btnAddNote = findViewById(R.id.btnAddNote);
        inputSearch = findViewById(R.id.inputSearch);

        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        // Recibir ID de categoría
        categoryId = getIntent().getIntExtra("category_id", -1);

        btnAddNote.setOnClickListener(v -> addNote());
        inputSearch.setOnEditorActionListener((v, actionId, event) -> {
            String text = inputSearch.getText().toString().trim();
            searchNotes(text);
            return true;
        });

        loadNotes();
    }

    private void loadNotes() {
        new Thread(() -> {
            List<Note> notes = db.noteDao().getNotesByCategory(categoryId);

            runOnUiThread(() -> {
                adapter = new NoteAdapter(notes);
                recyclerNotes.setAdapter(adapter);
            });
        }).start();
    }

    private void addNote() {
        new Thread(() -> {
            Note note = new Note(
                    "Es nueva nota ",
                    "Repasar para examen",
                    "01/12/2025",
                    categoryId
            );

            db.noteDao().insertNote(note);

            runOnUiThread(this::loadNotes);
        }).start();
    }

    private void searchNotes(String text) {
        new Thread(() -> {
            List<Note> results = db.noteDao().searchNotes(text);

            runOnUiThread(() -> {
                adapter = new NoteAdapter(results);
                recyclerNotes.setAdapter(adapter);
            });
        }).start();
    }
}

