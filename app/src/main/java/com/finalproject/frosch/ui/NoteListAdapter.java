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
//        switch (holder.getItemViewType()){
//            case 0:
//                Note note = notes.get(position);
//                holder.bind();
//                break;
//            case 1:
//                NoteHeader header = (NoteHeader)notes.get(position);
//
//                break;
//        }
        ((AbstractViewHolder) holder).bind(notes.get(position));
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

    public abstract static class AbstractViewHolder extends RecyclerView.ViewHolder{

        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(Note note);
    }

    public static class NoteViewHolder extends AbstractViewHolder implements View.OnClickListener{
        private final NoteItemBinding itemBinding;

        public NoteViewHolder(@NonNull NoteItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(Note note){
            this.itemBinding.name.setText(note.getName());
            this.itemBinding.comment.setText(note.getComment());
            if (note.getType().equals(TypeNote.INCOME.getName())) {
                this.itemBinding.sum.setText("+ " + note.getSum() + " ₽");
                this.itemBinding.sum.setTextColor(Color.parseColor("#1C9A21"));
            } else if (note.getType().equals(TypeNote.CONSUMPTION.getName())) {
                this.itemBinding.sum.setText("- " + note.getSum() + " ₽");
                this.itemBinding.sum.setTextColor(Color.parseColor("#C90C0C"));
            }
            ImageView view = this.itemBinding.icon;
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            String colorString = note.getColor();
            int color = Color.parseColor(colorString);
            drawable.setColor(color);
            this.itemBinding.icon.setImageResource(note.getIcon());
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
