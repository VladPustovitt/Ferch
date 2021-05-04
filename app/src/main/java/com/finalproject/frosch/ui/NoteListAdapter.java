package com.finalproject.frosch.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.frosch.R;
import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.databinding.DateHeaderBinding;
import com.finalproject.frosch.databinding.NoteItemBinding;

import java.util.LinkedList;

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final LinkedList<Note> notes;

    public NoteListAdapter(LinkedList<Note> notes) {
        this.notes = notes;
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
        return new NoteViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                Note note = notes.get(position);
                ((NoteViewHolder) holder).itemBinding.name.setText(note.getName());
                ((NoteViewHolder) holder).itemBinding.comment.setText(note.getComment());
                if (note.getType().equals(TypeNote.INCOME.getName())) {
                    ((NoteViewHolder) holder).itemBinding.sum.setText("+ " + note.getSum() + " ₽");
                    ((NoteViewHolder) holder).itemBinding.sum.setTextColor(Color.parseColor("#1C9A21"));
                } else if (note.getType().equals(TypeNote.CONSUMPTION.getName())) {
                    ((NoteViewHolder) holder).itemBinding.sum.setText("- " + note.getSum() + " ₽");
                    ((NoteViewHolder) holder).itemBinding.sum.setTextColor(Color.parseColor("#C90C0C"));
                }

                ImageView view = ((NoteViewHolder) holder).itemBinding.icon;
                GradientDrawable drawable = (GradientDrawable) view.getBackground();
                String colorString = note.getColor();
                int color = Color.parseColor(colorString);
                drawable.setColor(color);

                ((NoteViewHolder) holder).itemBinding.icon.setImageResource(note.getIcon());
                break;
            case 1:
                NoteHeader header = (NoteHeader)notes.get(position);
                ((HeaderViewHolder) holder).headerBinding.headerTitle.setText(header.getName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (((NoteHeader) notes.get(position)).getHeader() != null) {
                return 1;
            }
        } catch (ClassCastException e){
            return 0;
        }
        return 0;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        private final NoteItemBinding itemBinding;

        public NoteViewHolder(@NonNull NoteItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @Override
        public void onClick(View v) {

        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        private final DateHeaderBinding headerBinding;

        public HeaderViewHolder(@NonNull DateHeaderBinding headerBinding) {
            super(headerBinding.getRoot());
            this.headerBinding = headerBinding;
        }
    }
}
