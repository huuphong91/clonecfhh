package com.example.thecoffeehouse.update.editimage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;

import com.example.thecoffeehouse.data.model.User.User;
import com.example.thecoffeehouse.update.editfirstname.IEditFirstNameContract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class EditImagePresenter implements IEditFirstNameContract.Presenter {

    private IEditImageContract.View callback;
    private User mUser;
    DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private SharedPreferences mPrefs;
    private ProgressDialog dialog;

    public EditImagePresenter(IEditImageContract.View callback){
        this.callback = callback;
        mPrefs = callback.getContextt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
        dialog = new ProgressDialog(callback.getContextt());
        dialog.setTitle("Uploading");
        dialog.setMessage("Please wait!!!");
        dialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void editLastName(String numberPhone, String imageBitmap) {
        Gson gson = new Gson();
        String json = mPrefs.getString("myObject", null);
        mUser = gson.fromJson(json, User.class);
        dialog.show();
        mDataRef.child(numberPhone).child("image").setValue(imageBitmap).addOnCompleteListener(task -> {
            mUser.setImage(imageBitmap);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(mUser);
            editor.putString("myObject",json1);
            editor.commit();
            dialog.dismiss();
            callback.onChangeSuccess("OKE");
        });
    }
}
