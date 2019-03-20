package com.teamducati.cloneappcfh.entity;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "notification_table")
@Getter
@Setter
public class Notification implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    @ColumnInfo(name = "tilte")
    private String tilte;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "url")
    private String url;

    public Notification(String tilte, String content, String date,String url) {
        this.tilte = tilte;
        this.content = content;
        this.date = date;
        this.url = url;
    }
}
