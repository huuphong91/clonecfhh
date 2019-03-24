package com.example.thecoffeehouse.data.model.notification;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification")
public class Notification {

    @PrimaryKey (autoGenerate = true)
    @SerializedName("id")
    private int idNotifi;

    @SerializedName("title")
    private String title;

    @SerializedName("messege")
    private String messege;

    @SerializedName("imageNotification")
    private String imageNotification;

    public Notification( String title, String messege, String imageNotification) {
        this.title = title;
        this.messege = messege;
        this.imageNotification = imageNotification;
    }
    @Ignore
    public Notification() {
    }

    public int getIdNotifi() {
        return idNotifi;
    }

    public void setIdNotifi(int idNotifi) {
        this.idNotifi = idNotifi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getImageNotification() {
        return imageNotification;
    }

    public void setImageNotification(String imageNotification) {
        this.imageNotification = imageNotification;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idNotifi=" + idNotifi +
                ", title='" + title + '\'' +
                ", messege='" + messege + '\'' +
                ", imageNotification='" + imageNotification + '\'' +
                '}';
    }
}
