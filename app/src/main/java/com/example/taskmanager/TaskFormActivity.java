package com.example.taskmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskFormActivity extends AppCompatActivity {

    private EditText inputTitle, inputDescription, inputCreatedAt;
    private CheckBox checkCompleted;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        inputCreatedAt = findViewById(R.id.inputCreatedAt);
        checkCompleted = findViewById(R.id.checkCompleted);

        Button btnSaveTask = findViewById(R.id.btnSaveTask);
        db = AppDatabase.getInstance(this);

        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = inputTitle.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();
        String date = inputCreatedAt.getText().toString().trim();
        boolean completed = checkCompleted.isChecked();

        if (title.isEmpty()) {
            inputTitle.setError("El título es obligatorio");
            return;
        }

        if (date.isEmpty()) {
            inputCreatedAt.setError("La fecha es obligatoria");
            return;
        }

        Task task = new Task(title, description, date, completed);

        new Thread(() -> {
            db.taskDao().insertTask(task);
            runOnUiThread(() -> {
                Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();
                finish(); // Regresa a MainActivity
            });
        }).start();
    }
}

