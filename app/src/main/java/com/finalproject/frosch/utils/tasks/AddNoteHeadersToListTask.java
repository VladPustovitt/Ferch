package com.finalproject.frosch.utils.tasks;

import android.os.AsyncTask;

import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.ui.history.NoteHeader;

import java.util.LinkedList;

public class AddNoteHeadersToListTask extends AsyncTask<LinkedList<Note>, Void, Void> {
    @Override
    protected Void doInBackground(LinkedList<Note>... linkedLists) {
        NoteHeader.addNoteHeadersInList(linkedLists[0]);
        return null;
    }
}
