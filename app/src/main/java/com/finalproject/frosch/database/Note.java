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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ColumnInfo(name="icon")
    private String icon;

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
                int sum, Long date, String color) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.color = color;
        this.icon = "ic_dish";
    }

    @Ignore
    public Note(String type, String name, String comment,
                int sum, Long date, String color) {
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.color = color;
        this.icon = "ic_dish";
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
