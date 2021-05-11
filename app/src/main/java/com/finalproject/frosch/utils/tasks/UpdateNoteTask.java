package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;

public class UpdateNoteTask extends AsyncTask<Note, Void, Void> {

    private final AppDatabase database;

    public UpdateNoteTask(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        database.getNoteDao().updateNote(notes[0]);
        return null;
    }
}
