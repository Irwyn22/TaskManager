package com.example.taskmanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class HistoryActivity extends AppCompatActivity {

    private Spinner spinnerAction;
    private AppDatabase db;
    private RecyclerView recyclerHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerHistory = findViewById(R.id.recyclerHistory);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        loadHistory();

        spinnerAction = findViewById(R.id.spinnerAction);

        String[] actions = {"Todos", "insert_task", "update_task", "delete_task"};

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                actions
        );
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAction.setAdapter(adapterSpinner);

        spinnerAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadHistory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void loadHistory() {
        new Thread(() -> {
            String selectedAction = spinnerAction.getSelectedItem().toString();
            List<History> historyList;

            if (selectedAction.equals("Todos")) {
                historyList = db.historyDao().getAllHistory();
            } else {
                historyList = db.historyDao().getHistoryByAction(selectedAction);
            }

            runOnUiThread(() -> {
                HistoryAdapter adapter = new HistoryAdapter(historyList);
                recyclerHistory.setAdapter(adapter);
            });
        }).start();
    }
}