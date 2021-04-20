package com.finalproject.financeapp.database;

import android.os.AsyncTask;

import java.util.ArrayList;

public class DownloadDatabaseTask extends AsyncTask<AppDatabase, Void, ArrayList<Note>> {

    @Override
    protected ArrayList<Note> doInBackground(AppDatabase... appDatabases) {
        return (ArrayList<Note>) appDatabases[0].getNoteDao().getAllNotes();
    }
}
