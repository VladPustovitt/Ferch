package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;

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
