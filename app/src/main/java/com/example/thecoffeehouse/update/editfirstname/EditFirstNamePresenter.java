package com.example.thecoffeehouse.update.editfirstname;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.thecoffeehouse.data.model.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class EditFirstNamePresenter implements IEditFirstNameContract.Presenter {

    private User mUser;
    private IEditFirstNameContract.View callback;
    DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private SharedPreferences mPrefs;

    public EditFirstNamePresenter(IEditFirstNameContract.View callback){
        this.callback = callback;
        mPrefs = callback.getContextt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);

    }

    @Override
    public void editLastName(String numberPhone, String firstName) {
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        mUser = gson.fromJson(json, User.class);
        mDataRef.child(numberPhone).child("firstName").setValue(firstName).addOnCompleteListener(task -> {
            mUser.setFirstName(firstName);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(mUser);
            editor.putString("myObject",json1);
            editor.commit();
            callback.onEditSuccess(firstName);
        });
    }
}
