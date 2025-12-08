package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerCategories;
    private CategoryAdapter adapter;
    private AppDatabase db;
    private Button btnAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        recyclerCategories = findViewById(R.id.recyclerCategories);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        recyclerCategories.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        btnAddCategory.setOnClickListener(v -> addCategory());

        loadCategories();
    }

    private void loadCategories() {
        new Thread(() -> {
            List<Category> list = db.categoryDao().getAllCategories();

            runOnUiThread(() -> {
                adapter = new CategoryAdapter(list, category -> {
                    // Abrir NotesActivity y enviar category_id
                    Intent i = new Intent(CategoriesActivity.this, NotesActivity.class);
                    i.putExtra("category_id", category.getCategoryId());
                    startActivity(i);
                });

                recyclerCategories.setAdapter(adapter);
            });

        }).start();
    }

    private void addCategory() {
        new Thread(() -> {
            db.categoryDao().insertCategory(new Category("Nueva categoría"));
            runOnUiThread(this::loadCategories);
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategories();
    }
}
