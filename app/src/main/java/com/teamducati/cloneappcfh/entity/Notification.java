package com.teamducati.cloneappcfh.entity;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_table")
public class Notification implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "tilte")
    private String tilte;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "date")
    private String date;

    public Notification() {
    }

    public Notification(String tilte, String content, String date) {
        this.tilte = tilte;
        this.content = content;
        this.date = date;
    }

    public Notification(int _id, String tilte, String content, String date) {
        this._id = _id;
        this.tilte = tilte;
        this.content = content;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "_id=" + _id +
                ", tilte='" + tilte + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
