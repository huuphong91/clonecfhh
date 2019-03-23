package com.example.thecoffeehouse.update.editimage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.User.User;
import com.example.thecoffeehouse.update.editfirstname.IEditFirstNameContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import androidx.annotation.NonNull;

public class EditImagePresenter implements IEditImageContract.Presenter {

    private FirebaseStorage mFirebaseStore = FirebaseStorage.getInstance();
    private StorageReference mStorageRef = mFirebaseStore.getReferenceFromUrl("gs://thecoffeehouse-279bb.appspot.com");
    private StorageReference mImageRef;
    private StorageReference mFilePathImage;
    private IEditImageContract.View callback;
    private User mUser;
    DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private SharedPreferences mPrefs;
//    private ProgressDialog dialog;
    private KProgressHUD hud;

    public EditImagePresenter(IEditImageContract.View callback){
        this.callback = callback;
        mPrefs = callback.getContextt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
        hud = KProgressHUD.create(callback.getContextt())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Uploading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
//        dialog = new ProgressDialog(callback.getContextt());
//        dialog.setTitle("Uploading Image");
//        dialog.setMessage("Please Wait!");
    }

    @Override
    public void editImage(String numberPhone, String linkOldImage,Bitmap imageBitmapNew) {
//        dialog.show();
        hud.show();
        mImageRef = mFirebaseStore.getReferenceFromUrl(linkOldImage);
        mImageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(callback.getContextt(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                mFilePathImage = mStorageRef.child("image").child("image"+calendar.getTimeInMillis()+".png");
                ByteArrayOutputStream baosImage = new ByteArrayOutputStream();
                imageBitmapNew.compress(Bitmap.CompressFormat.PNG,50,baosImage);
                byte[] dataImage =baosImage.toByteArray();

                UploadTask uploadTaskImage = mFilePathImage.putBytes(dataImage);
                uploadTaskImage.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(callback.getContextt(), "That bai", Toast.LENGTH_SHORT).show();
                        callback.onChangeFail("Fail");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mFilePathImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadImageUri = uri;
                                String linkImageNew = downloadImageUri.toString();
                                Gson gson = new Gson();
                                String json = mPrefs.getString("myObject", null);
                                mUser = gson.fromJson(json, User.class);
                                mDataRef.child(numberPhone).child("image").setValue(linkImageNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d( "onComplete: ","OKE");
                                        mUser.setImage(linkImageNew);
                                        SharedPreferences.Editor editor = mPrefs.edit();
                                        editor.clear();
                                        Gson gson1 = new Gson();
                                        String json1 = gson1.toJson(mUser);
                                        editor.putString("myObject",json1);
                                        editor.apply();
//                                        callback.onChangeSuccess("Change Success!");
//                                        dialog.dismiss();
                                        hud.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d( "onComplete: ","Fail");

                                        hud.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hud.dismiss();
            }
        });
    }


//    @Override
//    public void changeImage(String numberPhone, Bitmap imageBitmap) {
//        Toast.makeText(callback.getContextt(), "Chua Vo", Toast.LENGTH_SHORT).show();
//        dialog.show();
//        String image = BitMapToString(imageBitmap);
//        Gson gson = new Gson();
//        String json = mPrefs.getString("myObject", null);
//        mUser = gson.fromJson(json, User.class);
//        mDataRef.child(numberPhone).child("image").setValue(image).addOnCompleteListener(task -> {
//            Toast.makeText(callback.getContextt(), "Vo Roi", Toast.LENGTH_SHORT).show();
//            mUser.setImage(image);
//            SharedPreferences.Editor editor = mPrefs.edit();
//            editor.clear();
//            Gson gson1 = new Gson();
//            String json1 = gson1.toJson(mUser);
//            editor.putString("myObject",json1);
//            editor.commit();
//            dialog.dismiss();
//            callback.onChangeSuccess("Changed");
//        });
//    }



}