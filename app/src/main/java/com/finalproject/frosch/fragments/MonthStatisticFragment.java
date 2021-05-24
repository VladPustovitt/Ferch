package com.finalproject.frosch.fragments;

import android.app.AlertDialog;
import android.content.Entity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.HashTag;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.databinding.CategoryDialogBinding;
import com.finalproject.frosch.databinding.MonthStatisticFragmentBinding;
import com.finalproject.frosch.ui.statistic.PieChartListAdapter;
import com.finalproject.frosch.utils.convertor.DateConvector;
import com.finalproject.frosch.utils.tasks.GetAllDateTask;
import com.finalproject.frosch.utils.tasks.GetNotesFromPeriodTask;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MonthStatisticFragment extends Fragment
implements View.OnClickListener {
    private MonthStatisticFragmentBinding binding;
    private AppDatabase database;
    private ArrayList<String> dateList;
    private ArrayList<Note> notesFromPeriod;
    private int idCurrentMonth;
    private PieChartListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MonthStatisticFragmentBinding.inflate(inflater, container, false);
        database = AppDatabase.getInstance(inflater.getContext());
        binding.previous.setOnClickListener(this);
        binding.next.setOnClickListener(this);
        try {
            dateList = DateConvector.getListOfMonthWithYear(new GetAllDateTask().execute(database).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (dateList.size() > 0){
            idCurrentMonth = 0;
            drawPieCharts(idCurrentMonth);
        }
        binding.chartCon.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        binding.chartCon.setAdapter(adapter);
        return binding.getRoot();
    }

    private String getMonthWithYear(int id){
        String[] date = dateList.get(id).split("\\.");
        return DateConvector.numStringToMonth(date[0]) + " " + date[1];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
                if(idCurrentMonth + 1 == dateList.size()){
                    Toast.makeText(getContext(), "Это последний месяц", Toast.LENGTH_SHORT).show();
                    return;
                }
                idCurrentMonth += 1;
                break;
            case R.id.next:
                if(idCurrentMonth == 0){
                    Toast.makeText(getContext(), "Это первый месяц", Toast.LENGTH_SHORT).show();
                    return;
                }
                idCurrentMonth -= 1;
                break;
        }
        drawPieCharts(idCurrentMonth);
        binding.chartCon.setAdapter(adapter);
    }

    private void drawPieCharts(int idMonth){
        binding.month.setText(getMonthWithYear(idMonth));
        ArrayList<String> consumptions = HashTag.Consumption.getNames();
        ArrayList<String> incomes = HashTag.Income.getNames();
        ArrayList<Pair<Float, String>> consumptionList = new ArrayList<>();
        for(String consumption: consumptions) {
            float sum = sumHashTag(consumption, idMonth);
            consumptionList.add(new Pair<>(sum, consumption));
        }
        ArrayList<Pair<Float, String>> incomeList = new ArrayList<>();
        for(String income: incomes) {
            float sum = sumHashTag(income, idMonth);
            incomeList.add(new Pair<>(sum, income));
        }
        LinkedList<ArrayList<Pair<Float, String>>> listAdapter = new LinkedList<>();
        listAdapter.add(consumptionList);
        listAdapter.add(incomeList);
        adapter = new PieChartListAdapter(listAdapter);
    }

    private Float sumHashTag(String hashTag, int idMonth){
        String date = dateList.get(idMonth);
        try {
            notesFromPeriod = new GetNotesFromPeriodTask(database).execute(new Pair<>(
                    DateConvector.dateStringToMs("01."+date+" 00:00"),
                    DateConvector.dateStringToMs(
                            "01." + DateConvector.addZeros(
                                    Integer.toString(Integer.parseInt(date.split("\\.")[0]) + 1)) +
                                    "."+date.split("\\.")[1] + " 00:00")
            )).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        float sum = 0;
        for(Note note: notesFromPeriod){
            if(note.getHashTag().equals(hashTag)){
                sum += note.getSum();
            }
        }
        return sum;
    }
}
