package com.finalproject.frosch.database;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.Objects;

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

    @ColumnInfo(name="hashTag")
    private String hashTag;

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

    public String getHashTag() {
        return hashTag;
    }
    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public Note(int id, String type, String name, String comment,
                int sum, Long date, String color, int icon, String hashTag) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.color = color;
        this.icon = icon;
        this.hashTag = hashTag;
    }

    @Ignore
    public Note(String type, String name, String comment,
                int sum, Long date, String color, int icon, String hashTag) {
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.sum = sum;
        this.date = date;
        this.color = color;
        this.icon = icon;
        this.hashTag = hashTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                sum == note.sum &&
                icon == note.icon &&
                Objects.equals(type, note.type) &&
                Objects.equals(name, note.name) &&
                Objects.equals(comment, note.comment) &&
                Objects.equals(date, note.date) &&
                Objects.equals(color, note.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, comment, date, sum, color, icon);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"type\":\"" + type + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"comment\":\"" + comment + "\"," +
                "\"date\":\"" + date + "\","+
                "\"sum\":\"" + sum + "\"," +
                "\"color\":\"" + color + "\"," +
                "\"icon\":\"" + icon + "\"," +
                "\"hashTag\":\"" + hashTag + "\"" +
                '}';
    }

    public static Note fromString(String noteString){
        Log.e("Note", noteString);
        Gson gson = new Gson();
        return gson.fromJson(noteString, Note.class);
    }
}
