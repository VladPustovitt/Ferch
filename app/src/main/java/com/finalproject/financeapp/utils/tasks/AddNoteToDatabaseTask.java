package com.finalproject.financeapp.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.financeapp.database.AppDatabase;
import com.finalproject.financeapp.database.Note;


public class AddNoteToDatabaseTask extends AsyncTask<Note, Void, Void> {

    private final AppDatabase database;

    public AddNoteToDatabaseTask(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        database.getNoteDao().insertAll(notes);
        return null;
    }
}
