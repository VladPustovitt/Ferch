package com.finalproject.frosch.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.databinding.MonthStatisticFragmentBinding;
import com.finalproject.frosch.utils.convertor.DateConvector;
import com.finalproject.frosch.utils.tasks.GetAllDateTask;
import com.finalproject.frosch.utils.tasks.GetNotesFromPeriodTask;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MonthStatisticFragment extends Fragment
implements View.OnClickListener {
    private MonthStatisticFragmentBinding binding;
    private AppDatabase database;
    private ArrayList<Long> dateMsList;
    private ArrayList<Note> notesFromPeriod;
    private Long currentMonth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MonthStatisticFragmentBinding.inflate(inflater, container, false);
        database = AppDatabase.getInstance(inflater.getContext());

        try {
            dateMsList = new GetAllDateTask().execute(database).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (dateMsList != null){
            currentMonth = dateMsList.get(0);

            
        }

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
                return;
            case R.id.next:
                return;
        }
    }
}
