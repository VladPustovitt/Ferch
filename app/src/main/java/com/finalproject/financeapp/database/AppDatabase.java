package com.finalproject.financeapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.finalproject.financeapp.activities.MainActivity;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract NoteDao getNoteDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context,
                        AppDatabase.class, MainActivity.dataName).build();
            }
        }
        return instance;
    }
}
