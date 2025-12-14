package com.example.taskmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskFormActivity extends AppCompatActivity {

    private EditText inputTitle, inputDescription;
    private CheckBox checkCompleted;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        checkCompleted = findViewById(R.id.checkCompleted);

        Button btnSaveTask = findViewById(R.id.btnSaveTask);
        db = AppDatabase.getInstance(this);

        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {

        String title = inputTitle.getText().toString().trim();
        String description = inputDescription.getText().toString().trim();
        boolean isCompleted = checkCompleted.isChecked();

        String date = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
        ).format(new Date());

        if (title.isEmpty()) {
            inputTitle.setError("El título es obligatorio");
            return;
        }

        new Thread(() -> {

            // 1. Guardar tarea
            Task task = new Task(title, description, date, isCompleted);
            db.taskDao().insertTask(task);

            // 2. Guardar historial
            History history = new History(
                    "insert_task",
                    date,
                    "Tarea creada: " + title
            );
            db.historyDao().insertHistory(history);

            // 3. Cerrar pantalla
            runOnUiThread(this::finish);

        }).start();
    }
}

