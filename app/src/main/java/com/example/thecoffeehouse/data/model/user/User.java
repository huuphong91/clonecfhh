package com.example.thecoffeehouse.data.model.user;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "users")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "first_name")
    private String mFirstName;

    @ColumnInfo(name = "last_name")
    private String mLastName;

    @ColumnInfo(name = "date_of_birth")
    private String mBirthday;

    @NonNull
    @ColumnInfo(name = "phone_number")
    private String mPhoneNumber;

    @ColumnInfo(name = "gender")
    private String mGender;

    @ColumnInfo(name = "address")
    private String mAddress;

    @ColumnInfo(name = "image")
    private String mImage;

    @Ignore
    public User() {
    }

    public User(String mFirstName, String mLastName, String mBirthday, String mPhoneNumber, String mGender, String mAddress, String mImage) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mBirthday = mBirthday;
        this.mPhoneNumber = mPhoneNumber;
        this.mGender = mGender;
        this.mAddress = mAddress;
        this.mImage = mImage;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }
}
