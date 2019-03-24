package com.example.thecoffeehouse.update.editbirthday;

import android.app.Activity;
import android.content.SharedPreferences;
import com.example.thecoffeehouse.RxBus;
import com.example.thecoffeehouse.data.model.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditBirthDayPresenter implements IEditBirhDayContract.Presenter  {

    private User mUser;
    private IEditBirhDayContract.View callback;
    DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private SharedPreferences mPrefs;

    public EditBirthDayPresenter(IEditBirhDayContract.View callback) {
        this.callback = callback;
        mPrefs = callback.getContextt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
    }

    @Override
    public void changeBirthDay(String numberPhone, String birthDay) {
        mDataRef.child(numberPhone).child("birthday").setValue(birthDay).addOnCompleteListener(task -> {
            boolean isDateTrue = isValidDate(birthDay);
            if(isDateTrue){
                Gson gson = new Gson();
                String json = mPrefs.getString("myObject", null);
                mUser = gson.fromJson(json,User.class);
                mUser.setBirthday(birthDay);
                SharedPreferences.Editor editor = mPrefs.edit();
                Gson gson1 = new Gson();
                String json1 = gson1.toJson(mUser);
                editor.putString("myObject",json1);
                editor.apply();

                callback.onChangeBirthdaySuccess(birthDay);
                RxBus.getInstance().postEvent(mUser);
            }else {
                callback.onChangeBirthdayFail("Check again format date. Please!!!!!");
            }
        });
    }

    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
