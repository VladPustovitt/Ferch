package com.finalproject.financeapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM Notes")
    void deleteAll();

    @Query("SELECT * FROM Notes")
    List<Note> getAllNotes();

    @Query("SELECT * FROM Notes WHERE date LIKE :date")
    List<Note> getNotesForDate(String date);
}
