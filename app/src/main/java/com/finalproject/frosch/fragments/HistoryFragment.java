package com.finalproject.frosch.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.frosch.activities.AddPostActivity;
import com.finalproject.frosch.ui.history.NoteHeader;
import com.finalproject.frosch.ui.history.NoteListAdapter;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.utils.tasks.DownloadDatabaseTask;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.databinding.HistoryFragmentBinding;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class HistoryFragment extends Fragment {
    private HistoryFragmentBinding historyBinding;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        historyBinding = HistoryFragmentBinding.inflate(inflater, container, false);
        database = AppDatabase.getInstance(inflater.getContext());
        setUpRecyclerView(inflater);
        historyBinding.plusPost.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPostActivity.class);
            startActivity(intent);
        });
        return historyBinding.getRoot();
    }

    private void setUpRecyclerView(@NonNull LayoutInflater inflater){
        LinkedList<Note> noteList = new LinkedList<>();
        try {
            noteList = new LinkedList<>(
                    new DownloadDatabaseTask().execute(database).get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        NoteHeader.addNoteHeadersInList(noteList);

        NoteListAdapter noteAdapter = new NoteListAdapter(noteList, getActivity());
        historyBinding.historyList.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        historyBinding.historyList.setAdapter(noteAdapter);
    }
}