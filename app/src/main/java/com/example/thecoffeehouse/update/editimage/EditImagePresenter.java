package com.example.thecoffeehouse.update.editimage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.thecoffeehouse.data.model.bill.Bill;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
    DatabaseReference mDataRefData = FirebaseDatabase.getInstance().getReference("Bill");
    private ArrayList<Bill> billArrayList = new ArrayList<>();

    private SharedPreferences mPrefs;
//    private ProgressDialog dialog;
    private KProgressHUD hud;

    public EditImagePresenter(IEditImageContract.View callback){
        this.callback = callback;
        mPrefs = callback.getContextt().getSharedPreferences("dataUser", Activity.MODE_PRIVATE);
        hud = KProgressHUD.create(callback.getContextt())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
//        dialog = new ProgressDialog(callback.getContextt());
//        dialog.setTitle("Uploading Image");
//        dialog.setMessage("Please Wait!");
    }


    @Override
    public void editImage(String numberPhone, String linkOldImage,  Bitmap imageBitmapNew) {
//        dialog.show();
        hud.show();
        mImageRef = mFirebaseStore.getReferenceFromUrl(linkOldImage);
        mImageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Calendar calendar = Calendar.getInstance();
                mFilePathImage = mStorageRef.child("image").child("image"+calendar.getTimeInMillis()+".png");
                ByteArrayOutputStream baosImage = new ByteArrayOutputStream();
                Bitmap imageBitmapNew2 = getResizedBitmap(imageBitmapNew,100,100);
                imageBitmapNew2.compress(Bitmap.CompressFormat.PNG,50,baosImage);
                byte[] dataImage =baosImage.toByteArray();

                UploadTask uploadTaskImage = mFilePathImage.putBytes(dataImage);
                uploadTaskImage.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}