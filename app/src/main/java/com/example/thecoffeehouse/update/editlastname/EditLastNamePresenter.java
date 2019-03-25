package com.example.thecoffeehouse.update.editlastname;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.thecoffeehouse.RxBus;
import com.example.thecoffeehouse.data.model.bill.Bill;
import com.example.thecoffeehouse.data.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class EditLastNamePresenter implements IEditLastNameContract.Presenter {

    private User mUser;
    private IEditLastNameContract.View callback;
    DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    DatabaseReference mDataRefData = FirebaseDatabase.getInstance().getReference("Bill");
    private SharedPreferences mPrefs;
//    private ProgressDialog dialog;

    public EditLastNamePresenter(IEditLastNameContract.View callback){
        this.callback = callback;
        mPrefs = callback.getContextt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
//        dialog = new ProgressDialog(callback.getContextt());
//        dialog.setTitle("Uploading");
//        dialog.setMessage("Please wait!!!");
//        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void editLastName(String numberPhone, String lastName) {
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        mUser = gson.fromJson(json,User.class);
//        dialog.show();
        mDataRef.child(numberPhone).child("lastName").setValue(lastName).addOnCompleteListener(task -> {
            mUser.setLastName(lastName);
            SharedPreferences.Editor editor = mPrefs.edit();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(mUser);
            editor.putString("myObject",json1);
            editor.apply();
//            dialog.dismiss();
            callback.onEditSuccess(lastName);
            RxBus.getInstance().postEvent(mUser);
        });
    }


}
