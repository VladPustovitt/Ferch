package com.finalproject.financeapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.financeapp.activities.AddPostActivity;
import com.finalproject.financeapp.ui.NoteListAdapter;
import com.finalproject.financeapp.database.AppDatabase;
import com.finalproject.financeapp.utils.tasks.DeleteNoteInDatabaseTask;
import com.finalproject.financeapp.utils.tasks.DownloadDatabaseTask;
import com.finalproject.financeapp.database.Note;
import com.finalproject.financeapp.databinding.HistoryFragmentBinding;
import com.finalproject.financeapp.ui.SwipeToDeleteCallback;

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
                Collections.reverse(noteList);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (historyBinding.historyList.getAdapter() == null){
            noteAdapter = new NoteListAdapter(noteList);
            historyBinding.historyList.setLayoutManager(
                    new LinearLayoutManager(inflater.getContext()));
            historyBinding.historyList.setAdapter(noteAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(inflater.getContext()) {
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    new DeleteNoteInDatabaseTask(database)
                            .execute(noteAdapter.getNotes().get(viewHolder.getAdapterPosition()));
                    noteAdapter.remote(viewHolder.getAdapterPosition());
                }
            });
            itemTouchHelper.attachToRecyclerView(historyBinding.historyList);
        } else {
            noteAdapter.notifyDataSetChanged();
        }

        historyBinding.plusPost.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPostActivity.class);
            startActivity(intent);
        });
        return historyBinding.getRoot();
    }
}