package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;


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
