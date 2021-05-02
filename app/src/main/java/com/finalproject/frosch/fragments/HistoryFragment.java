package com.finalproject.frosch.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.frosch.activities.AddPostActivity;
import com.finalproject.frosch.ui.NoteListAdapter;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.utils.convertor.DateConvector;
import com.finalproject.frosch.utils.tasks.DownloadDatabaseTask;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.databinding.HistoryFragmentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class HistoryFragment extends Fragment {
    private HistoryFragmentBinding historyBinding;
    private NoteListAdapter noteAdapter;
    private ArrayList<Note> noteList;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (noteList == null){
            noteList = new ArrayList<>();
            try {
                noteList = new DownloadDatabaseTask().execute(database).get();
                Collections.reverse(noteList);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            noteList.clear();
            try {
                noteList.addAll(new DownloadDatabaseTask().execute(database).get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (historyBinding.historyList.getAdapter() == null){
            noteAdapter = new NoteListAdapter(noteList, historyBinding);
            historyBinding.historyList.setLayoutManager(
                    new LinearLayoutManager(inflater.getContext()));
            historyBinding.historyList.setAdapter(noteAdapter);
        } else {
            noteAdapter.notifyDataSetChanged();
        }
    }
}