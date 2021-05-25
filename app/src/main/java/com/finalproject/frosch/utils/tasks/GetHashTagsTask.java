package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.frosch.database.AppDatabase;

import java.util.ArrayList;

public class GetHashTagsTask extends AsyncTask<AppDatabase, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(AppDatabase... appDatabases) {
        return (ArrayList<String>) appDatabases[0].getNoteDao().getHashTags();
    }
}
