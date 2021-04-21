package com.finalproject.financeapp.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.financeapp.database.AppDatabase;
import com.finalproject.financeapp.database.Note;

public class DeleteNoteInDatabaseTask extends AsyncTask<Note, Void, Void> {

    private final AppDatabase database;

    public DeleteNoteInDatabaseTask(AppDatabase database){this.database = database;}

    @Override
    protected Void doInBackground(Note... notes) {
        for(Note note: notes){
            this.database.getNoteDao().delete(note);
        }
        return null;
    }
}
