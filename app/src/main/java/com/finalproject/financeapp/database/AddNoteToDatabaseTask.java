package com.finalproject.financeapp.database;

import android.os.AsyncTask;


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
