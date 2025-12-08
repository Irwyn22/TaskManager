package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerTasks;
    private TaskAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerTasks = findViewById(R.id.recyclerTasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));

        Button btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskFormActivity.class);
            startActivity(intent);
        });

        db = AppDatabase.getInstance(this);

        loadTasks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        new Thread(() -> {

            // INSERTAR UNA TAREA DE PRUEBA

            List<Task> tasks = db.taskDao().getAllTasks();

            System.out.println("DEBUG: Se encontraron " + tasks.size() + " tareas");

            runOnUiThread(() -> {
                adapter = new TaskAdapter(tasks);
                recyclerTasks.setAdapter(adapter);
            });
        }).start();
    }

}
