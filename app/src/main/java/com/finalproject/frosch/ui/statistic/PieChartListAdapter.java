package com.finalproject.frosch.ui.statistic;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.frosch.R;
import com.finalproject.frosch.databinding.CategoryDialogBinding;
import com.finalproject.frosch.databinding.DiagramItemBinding;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class PieChartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LinkedList<ArrayList<Pair<Float, String>>> list;

    public PieChartListAdapter(LinkedList<ArrayList<Pair<Float, String>>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DiagramItemBinding binding = DiagramItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        if (viewType == 0){
            return new ConsumptionViewHolder(binding);
        }
        return new IncomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == 0) ((PieChartListAdapter.ConsumptionViewHolder) holder).bind();
        else if(position == 1) ((PieChartListAdapter.IncomeViewHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private abstract class AbstractViewHolder extends RecyclerView.ViewHolder{
        protected DiagramItemBinding binding;

        public AbstractViewHolder(@NonNull DiagramItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public abstract void bind();
    }

    public class ConsumptionViewHolder extends AbstractViewHolder{
        public ConsumptionViewHolder(@NonNull DiagramItemBinding binding) {
            super(binding);
        }

        @Override
        public void bind() {
            binding.header.setText("Расходы");
            ArrayList<PieEntry> entries = new ArrayList<>();
            for(Pair<Float, String> pair: list.get(0)){
                if(pair.first > 0.0){
                    entries.add(new PieEntry(pair.first, pair.second));
                }
            }
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(binding.getRoot().getResources().getIntArray(R.array.consumption));
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setValueTextSize(16);
            PieData data = new PieData(dataSet);
            binding.chartCon.setData(data);

            Legend legend = binding.chartCon.getLegend();
            legend.setTextSize(13);
            legend.setDrawInside(false);
            legend.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
            legend.setWordWrapEnabled(true);

            binding.chartCon.animateXY(2000, 2000);
            binding.chartCon.invalidate();

            binding.chartCon.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    String hashTag = ((PieEntry) e).getLabel();
                    float sum = ((PieEntry) e).getValue();
                    AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                    builder.setCancelable(true);
                    CategoryDialogBinding dialogBinding =
                            CategoryDialogBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()));
                    dialogBinding.category.setText(hashTag);
                    dialogBinding.sum.setText(Double.toString(sum) + " ₽");
                    dialogBinding.sum.setTextColor(binding.getRoot().getResources().getColor(R.color.consumption));
                    builder.setView(dialogBinding.getRoot());
                    builder.create().show();
                }
                @Override
                public void onNothingSelected() {
                }
            });

            Description d = new Description();
            d.setText("");
            binding.chartCon.setDescription(d);
        }
    }

    public class IncomeViewHolder extends AbstractViewHolder{

        public IncomeViewHolder(@NonNull DiagramItemBinding binding) {
            super(binding);
        }

        @Override
        public void bind() {
            binding.header.setText("Доходы");
            ArrayList<PieEntry> entries = new ArrayList<>();
            for(Pair<Float, String> pair: list.get(1)){
                if(pair.first > 0.0){
                    entries.add(new PieEntry(pair.first, pair.second));
                }
            }
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(binding.getRoot().getResources().getIntArray(R.array.income));
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setValueTextSize(16);
            dataSet.setValueTextColor(binding.getRoot().getResources().getColor(R.color.black));
            PieData data = new PieData(dataSet);
            binding.chartCon.setData(data);

            Legend legend = binding.chartCon.getLegend();
            legend.setTextSize(13);
            legend.setDrawInside(false);
            legend.setTextColor(binding.getRoot().getResources().getColor(R.color.black));
            legend.setWordWrapEnabled(true);

            binding.chartCon.animateXY(2000, 2000);
            binding.chartCon.invalidate();

            binding.chartCon.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    String hashTag = ((PieEntry) e).getLabel();
                    float sum = ((PieEntry) e).getValue();
                    AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                    builder.setCancelable(true);
                    CategoryDialogBinding dialogBinding =
                            CategoryDialogBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()));
                    dialogBinding.category.setText(hashTag);
                    dialogBinding.sum.setText(Double.toString(sum) + " ₽");
                    dialogBinding.sum.setTextColor(binding.getRoot().getResources().getColor(R.color.income));
                    builder.setView(dialogBinding.getRoot());
                    builder.create().show();
                }
                @Override
                public void onNothingSelected() {
                }
            });

            Description d = new Description();
            d.setText("");
            binding.chartCon.setDescription(d);
        }
    }
}
