package com.finalproject.frosch.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    private Long date;

    @ColumnInfo(name="sum")
    private int sum;

    @ColumnInfo(name="color")
    private String color;

    @ColumnInfo(name="icon")
    private int icon;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Note(int id, String type, String name, String comment,
                int sum, Long date, String color, int icon) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.color = color;
        this.icon = icon;
    }

    @Ignore
    public Note(String type, String name, String comment,
                int sum, Long date, String color, int icon) {
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.color = color;
        this.icon = icon;
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
