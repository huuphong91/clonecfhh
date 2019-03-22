package com.example.thecoffeehouse.firstupdate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;

public class FirstUpdatePresenter implements IFirstUpdateContract.Presenter {

    private IFirstUpdateContract.View callback;
    private DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private Bitmap imageBitmap;
//    private ProgressDialog dialog;
    private String image;
    private SharedPreferences mPrefs;

    public FirstUpdatePresenter(IFirstUpdateContract.View callback){
        this.callback = callback;
        mPrefs = callback.activity().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
        imageBitmap = BitmapFactory.decodeResource(callback.activity().getResources(), R.drawable.img_bg_tch);
        image = BitMapToString(imageBitmap);
//        dialog = new ProgressDialog(callback.activity());
//        dialog.setTitle("Uploading");
//        dialog.setMessage("Please wait!!!");
//        dialog.setCanceledOnTouchOutside(false);

    }


    @Override
    public void insertUser(String numberPhone, String firstName, String lastName) {
//        dialog.show();
        User user = new User(firstName, lastName, "[Chưa xác định]", numberPhone, "[Chưa xác định]", "", image);
        mDataRef.child(numberPhone).setValue(user).addOnCompleteListener(task -> {

            mDataRef.child(numberPhone).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount() != 0){
                        User mUser = dataSnapshot.getValue(User.class);
                        SharedPreferences.Editor editor = mPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(mUser);
                        editor.putString("myObject",json);
                        editor.commit();
                        callback.insertUserSuccess("Insert Thanh Cong");
//                        dialog.dismiss();
                    }
                    else {
//                        dialog.dismiss();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.insertUserFail("Fail");
                }
            });
//            dialog.dismiss();
        });
    }

    private String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
