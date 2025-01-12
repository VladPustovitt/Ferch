package com.finalproject.frosch.ui.history;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.frosch.R;
import com.finalproject.frosch.activities.UpdatePostActivity;
import com.finalproject.frosch.database.AppDatabase;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.databinding.DateHeaderBinding;
import com.finalproject.frosch.databinding.NoteItemBinding;
import com.finalproject.frosch.utils.convertor.valuteconvertor.ValuteConvector;
import com.finalproject.frosch.utils.tasks.DeleteNoteInDatabaseTask;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.LinkedList;

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final LinkedList<Note> notes;
    private final Activity activity;

    public NoteListAdapter(LinkedList<Note> notes, Activity activity) {
        this.notes = notes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            DateHeaderBinding binding1 = DateHeaderBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new HeaderViewHolder(binding1);
        }
        NoteItemBinding binding = NoteItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent,false);
        return new NoteViewHolder(binding, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AbstractViewHolder) holder).bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return NoteHeader.isHeader(notes.get(position))? 1: 0;
    }

    public abstract static class AbstractViewHolder extends RecyclerView.ViewHolder{

        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(Note note);
    }

    public static class NoteViewHolder extends AbstractViewHolder{
        private final NoteItemBinding itemBinding;
        private final NoteListAdapter adapter;

        public NoteViewHolder(@NonNull NoteItemBinding itemBinding, NoteListAdapter adapter) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            this.adapter = adapter;
        }

        public void bind(Note note){

            SharedPreferences preferences = adapter.activity.getPreferences(Context.MODE_PRIVATE);
            String valute = preferences.getString("valute", "0");
            int position = adapter.notes.indexOf(note);
            this.itemBinding.name.setText(note.getName());
            this.itemBinding.comment.setText(note.getComment());
            if (note.getType().equals(TypeNote.INCOME.getName())) {
                String text = "+";
                text += new ValuteConvector(adapter.activity).convertFromRub(note.getSum(),
                        Integer.parseInt(valute));

                switch (valute) {
                    case "0":
                        text += "₽";
                        break;
                    case "1":
                        text += "$";
                        break;
                    case "2":
                        text += "€";
                        break;
                }

                this.itemBinding.sum.setText(text);
                this.itemBinding.sum.setTextColor(Color.parseColor("#1C9A21"));
            } else if (note.getType().equals(TypeNote.CONSUMPTION.getName())) {
                String text = "-";
                text += new ValuteConvector(adapter.activity).convertFromRub(note.getSum(),
                        Integer.parseInt(valute));

                switch (valute) {
                    case "0":
                        text += "₽";
                        break;
                    case "1":
                        text += "$";
                        break;
                    case "2":
                        text += "€";
                        break;
                }

                this.itemBinding.sum.setText(text);
                this.itemBinding.sum.setTextColor(Color.parseColor("#C90C0C"));
            }
            ImageView view = this.itemBinding.icon;
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            String colorString = note.getColor();
            int color = Color.parseColor(colorString);
            drawable.setColor(color);
            this.itemBinding.icon.setImageResource(note.getIcon());
            this.itemBinding.note.setOnClickListener(v -> {
                String[] items = new String[]{"Изменить", "Удалить"};
                new MaterialAlertDialogBuilder(v.getContext())
                        .setTitle(R.string.choose_action)
                        .setItems(items, ((dialog, which) -> {
                            switch (which){
                                case 0:
                                    Intent intent = new Intent(v.getContext(), UpdatePostActivity.class);
                                    intent.putExtra("CURRENT_NOTE", note.toString());
                                    v.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    new DeleteNoteInDatabaseTask(AppDatabase.getInstance(v.getContext()))
                                            .execute(note);
                                    this.adapter.notes.remove(note);
                                    NoteHeader.removeUnlessHeader(this.adapter.notes);
                                    this.adapter.notifyDataSetChanged();
                                    break;
                            }
                        }))
                        .show();
            });
        }
    }

    public static class HeaderViewHolder extends AbstractViewHolder{
        private final DateHeaderBinding headerBinding;

        public HeaderViewHolder(@NonNull DateHeaderBinding headerBinding) {
            super(headerBinding.getRoot());
            this.headerBinding = headerBinding;
        }

        @Override
        public void bind(Note note) {
            this.headerBinding.headerTitle.setText(((NoteHeader)note).getHeader());
        }
    }

}
