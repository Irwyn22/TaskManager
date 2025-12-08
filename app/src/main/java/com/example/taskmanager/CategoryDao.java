package com.example.taskmanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM categories ORDER BY category_name")
    List<Category> getAllCategories();

    @Transaction
    @Query("SELECT * FROM categories")
    List<CategoryWithNotes> getCategoriesWithNotes();
}


