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

public class MonthStatisticFragment extends Fragment {
    private MonthStatisticFragmentBinding binding;
    private AppDatabase database;
    private ArrayList<Long> dateMsList;
    private ArrayList<Note> notesFromPeriod;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MonthStatisticFragmentBinding.inflate(inflater, container, false);
        database = AppDatabase.getInstance(inflater.getContext());
        try {
            dateMsList = new GetAllDateTask().execute(database).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> monthWithYear = DateConvector.getListOfMonthWithYear(dateMsList);

        Long date1 = DateConvector.dateStringToMs("01."+monthWithYear.get(0) + " 00:00");
        Long date2 = DateConvector.dateStringToMs("01."+ "0"+(Integer.parseInt(monthWithYear.get(0).split("\\.")[0]) + 1) + ".2021 00:00") - 1;

        try {
            GetNotesFromPeriodTask task = new GetNotesFromPeriodTask(database);
            Pair<Long, Long> period = new Pair<>(date1, date2);
            notesFromPeriod = task.execute(period).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        binding.month.setText(monthWithYear.get(0));
        List<PieEntry> list = new ArrayList<>();

        for(Note note: notesFromPeriod){
            list.add(new PieEntry(note.getSum(), note));
        }

        PieDataSet set = new PieDataSet(list, "Test");
        set.setColors(getResources().getIntArray(R.array.post_color));
        PieData data = new PieData(set);
        binding.chart.setData(data);
        binding.chart.invalidate();
        return binding.getRoot();
    }
}
