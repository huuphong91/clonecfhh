package com.example.thecoffeehouse.firstupdate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import androidx.annotation.NonNull;

public class FirstUpdatePresenter implements IFirstUpdateContract.Presenter {

    private FirebaseStorage mFirebaseStore = FirebaseStorage.getInstance();
    private StorageReference mStorageRef = mFirebaseStore.getReferenceFromUrl("gs://thecoffeehouse-279bb.appspot.com");
    private IFirstUpdateContract.View callback;
    private DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private StorageReference mFilePathImage;
    private Bitmap imageBitmap;
    //    private ProgressDialog dialog;
    private String image;
    private SharedPreferences mPrefs;

    public FirstUpdatePresenter(IFirstUpdateContract.View callback){
        this.callback = callback;
        mPrefs = callback.activity().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);

//        image = BitMapToString(imageBitmap);
//        dialog = new ProgressDialog(callback.activity());
//        dialog.setTitle("Uploading");
//        dialog.setMessage("Please wait!!!");
//        dialog.setCanceledOnTouchOutside(false);

    }


//    @Override
//    public void insertUser(String numberPhone, String firstName, String lastName) {
////        dialog.show();
//        User user = new User(firstName, lastName, "[Chưa xác định]", numberPhone, "[Chưa xác định]", "", image);
//        mDataRef.child(numberPhone).setValue(user).addOnCompleteListener(task -> {
//
//            mDataRef.child(numberPhone).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.getChildrenCount() != 0){
//                        User mUser = dataSnapshot.getValue(User.class);
//                        SharedPreferences.Editor editor = mPrefs.edit();
//                        Gson gson = new Gson();
//                        String json = gson.toJson(mUser);
//                        editor.putString("myObject",json);
//                        editor.commit();
//                        callback.insertUserSuccess("Insert Thanh Cong");
////                        dialog.dismiss();
//                    }
//                    else {
////                        dialog.dismiss();
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    callback.insertUserFail("Fail");
//                }
//            });
////            dialog.dismiss();
//        });
//    }

    private String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void insertUser(String numberPhone, String firstName, String lastName) {
        Calendar calendar = Calendar.getInstance();
        mFilePathImage = mStorageRef.child("image").child("image"+calendar.getTimeInMillis()+".png");
        imageBitmap = BitmapFactory.decodeResource(callback.activity().getResources(), R.drawable.img_bg_tch);
        ByteArrayOutputStream baosImage = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG,50,baosImage);
        byte[] dataImage =baosImage.toByteArray();

        UploadTask uploadTaskImage = mFilePathImage.putBytes(dataImage);
        uploadTaskImage.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.insertUserFail("Fail");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mFilePathImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadImageUri = uri;
                        image = downloadImageUri.toString();
                        User user = new User(firstName, lastName, "[Chưa xác định]", numberPhone, "[Chưa xác định]", "", image);
                        mDataRef.child(numberPhone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
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
                                        }
                                        else {

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        callback.insertUserFail("Fail");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}