package com.finalproject.financeapp.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.financeapp.database.AppDatabase;
import com.finalproject.financeapp.database.Note;

import java.util.ArrayList;

public class DownloadDatabaseTask extends AsyncTask<AppDatabase, Void, ArrayList<Note>> {

    @Override
    protected ArrayList<Note> doInBackground(AppDatabase... appDatabases) {
        return (ArrayList<Note>) appDatabases[0].getNoteDao().getAllNotes();
    }
}
