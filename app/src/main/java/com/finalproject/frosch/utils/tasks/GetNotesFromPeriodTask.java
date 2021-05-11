package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import androidx.core.util.Pair;

import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;

import java.util.ArrayList;

public class GetNotesFromPeriodTask extends AsyncTask<Pair<Long, Long>, Void, ArrayList<Note>> {

    private AppDatabase database;

    public GetNotesFromPeriodTask(AppDatabase database){this.database = database;}

    @Override
    protected ArrayList<Note> doInBackground(Pair<Long, Long>... pairs) {
        return (ArrayList<Note>) database.getNoteDao().getNotesFromPeriod(pairs[0].first, pairs[0].second);
    }
}
