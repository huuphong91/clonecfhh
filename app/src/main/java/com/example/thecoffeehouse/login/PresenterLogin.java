package com.example.thecoffeehouse.login;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import com.example.thecoffeehouse.data.model.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import androidx.annotation.NonNull;


public class PresenterLogin implements IPresenterLoginContract.Presenter {

    private DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("Users");
    private IPresenterLoginContract.View callback;
    private SharedPreferences mPrefs;
    private ProgressDialog dialog;


    public PresenterLogin(IPresenterLoginContract.View callback){
        this.callback = callback;
        mPrefs = callback.activity().getSharedPreferences("dataUser",Activity.MODE_PRIVATE);
//        dialog = new ProgressDialog(callback.activity());
//        dialog.setTitle("Checking!!!");
//        dialog.setMessage("Please!!!");
//        dialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void checkFirstLogin(String numberPhone) {
//        dialog.show();
        if(!numberPhone.startsWith("0")){
            numberPhone = "0"+ numberPhone;
        }
        String finalNumberPhone = numberPhone;
        mDataRef.orderByKey().equalTo(numberPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onCheckSucess("Check Thanh Cong, Ton Tai User");
                    loadUser(finalNumberPhone);
                } else {
                    callback.onCheckFail("Chua Co User");
//                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCheckFail(databaseError.getMessage());
//                dialog.dismiss();
            }
        });
    }

    @Override
    public void loadUser(String numberPhone) {
//        if(numberPhone.startsWith("0")){
//
//        }
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
                    callback.onLoadSucess("Load Thanh Cong");
//                    dialog.dismiss();
                }
                else {
                    callback.onLoadFail("Kiem tra ket noi mang va thu lai");
//                    dialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onLoadFail(databaseError.getMessage());
//                dialog.dismiss();
            }
        });
    }
}
