package com.example.thecoffeehouse.update.editgender;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.thecoffeehouse.data.model.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class EditGenderPresenter implements IEditGenderContract.Presenter {

    private User mUser;
    private IEditGenderContract.View callback;
    DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private SharedPreferences mPrefs;

    public EditGenderPresenter(IEditGenderContract.View callback){
        this.callback = callback;
        mPrefs = callback.getContexttt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
    }

    @Override
    public void changeGender(String numberPhone,String gender) {
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        mUser = gson.fromJson(json, User.class);
        mDataRef.child(numberPhone).child("gender").setValue(gender).addOnCompleteListener(task -> {
            mUser.setGender(gender);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(mUser);
            editor.putString("myObject",json1);
            editor.commit();
            callback.onChangedSuccess(gender);
        });
    }
}
