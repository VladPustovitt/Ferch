package com.finalproject.frosch.ui;

import androidx.annotation.NonNull;

import com.finalproject.frosch.database.Note;
import com.finalproject.frosch.database.TypeNote;
import com.finalproject.frosch.utils.convertor.DateConvector;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class NoteHeader extends Note {
    private final String header;

    public NoteHeader(String header){
        super(TypeNote.HEADER.getName(), header, TypeNote.HEADER.getName(), 0, 0L, "", 0, "");
        this.header = header;
    }

    public static void addNoteHeadersInList(LinkedList<Note> list){
        if(list.size() != 0) {
            String lastHeader = DateConvector.msToDateString(list.get(0).getDate()).split(" ")[0];
            list.addFirst(new NoteHeader(DateConvector.getDateString(lastHeader)));
            for (int i = 0; i < list.size(); i++) {
                if (!(list.get(i) instanceof NoteHeader)) {
                    String date = DateConvector.msToDateString(list.get(i).getDate()).split(" ")[0];
                    if (!date.equals(lastHeader)) {
                        list.add(i, new NoteHeader(DateConvector.getDateString(date)));
                        lastHeader = date;
                    }
                }
            }
        }
    }

    public static void removeUnlessHeader(LinkedList<Note> list){
        if(list.size() != 0){
            for(int i = 1; i < list.size(); i++){
                if(isHeader(list.get(i-1)) && isHeader(list.get(i))){
                    list.remove(i-1);
                }
            }
            if(isHeader(list.getLast())) list.removeLast();
        }
    }

    public static boolean isHeader(Note note){
        try {
            if (((NoteHeader) note).getHeader() != null) {
                return true;
            }
        } catch (ClassCastException e){
            return false;
        }
        return false;
    }

    public String getHeader() {
        return header;
    }

    @NonNull
    @Override
    public String toString() {
        return "NoteHeader{" +
                "header='" + header + '\'' +
                '}';
    }
}
