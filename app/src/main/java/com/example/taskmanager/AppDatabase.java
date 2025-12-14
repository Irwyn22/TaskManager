package com.example.taskmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Task.class, Category.class, Note.class, History.class},
        version = 3
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract CategoryDao categoryDao();
    public abstract NoteDao noteDao();
    public abstract HistoryDao historyDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "task_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

