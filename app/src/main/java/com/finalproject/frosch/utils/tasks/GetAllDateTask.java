package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.frosch.database.AppDatabase;

import java.util.ArrayList;

public class GetAllDateTask extends AsyncTask<AppDatabase, Void, ArrayList<Long>> {

    @Override
    protected ArrayList<Long> doInBackground(AppDatabase... appDatabases) {
        return (ArrayList<Long>) appDatabases[0].getNoteDao().getAllDate();
    }
}
