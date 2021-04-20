package com.finalproject.financeapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="type")
    private String type;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="comment")
    private String comment;

    @ColumnInfo(name="date")
    private String date;

    @ColumnInfo(name="sum")
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Note(int id, String type, String name, String comment, int sum, String date) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
    }

    @Ignore
    public Note(String type, String name, String comment, int sum, String date) {
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                '}';
    }
}
