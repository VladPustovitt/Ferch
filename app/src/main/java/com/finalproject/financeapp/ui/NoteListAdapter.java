package com.finalproject.financeapp.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.financeapp.database.Note;
import com.finalproject.financeapp.database.TypeNote;
import com.finalproject.financeapp.databinding.NoteItemBinding;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private final ArrayList<Note> notes;

    public NoteListAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteItemBinding binding = NoteItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent,false);
        return new NoteViewHolder(binding, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.itemBinding.name.setText(note.getName());
        holder.itemBinding.comment.setText(note.getComment());
        if (note.getType().equals(TypeNote.INCOME.getName())){
            holder.itemBinding.sum.setText("+ " + note.getSum() + " ₽");
            holder.itemBinding.sum.setTextColor(Color.parseColor("#1C9A21"));
        } else if (note.getType().equals(TypeNote.CONSUMPTION.getName())){
            holder.itemBinding.sum.setText("- " + note.getSum() + " ₽");
            holder.itemBinding.sum.setTextColor(Color.parseColor("#C90C0C"));
        }
    }

    public void remote(int position){
        notes.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {
        private NoteItemBinding itemBinding;

        private final NoteListAdapter listAdapter;

        public NoteViewHolder(@NonNull NoteItemBinding itemBinding, NoteListAdapter listAdapter) {
            super(itemBinding.getRoot());
            this.listAdapter = listAdapter;
            this.itemBinding = itemBinding;
            this.itemBinding.note.setOnClickListener(this);
        }
        
        @SuppressLint("ShowToast")
        @Override
        public void onClick(View v) {
            Log.d("Item", "Click");
        }
    }
}
