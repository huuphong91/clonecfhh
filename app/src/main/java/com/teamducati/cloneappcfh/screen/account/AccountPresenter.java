package com.teamducati.cloneappcfh.screen.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AccountPresenter implements AccountContract.Presenter {

    private Context context;
    private AccountContract.View.AccountView mAccountView;
    private AccountContract.View.LoginView mLoginView;
    private AccountContract.View.ProfileView mProfileView;
    private AccountContract.View.UpdateView mUpdateView;

    private User userObj;

    public AccountPresenter(Context context, AccountContract.View view) {
        this.context = context;
        if(view instanceof AccountContract.View.AccountView){
            this.mAccountView = (AccountContract.View.AccountView) view;
            this.mAccountView.setPresenter(this);
        }
        if(view instanceof AccountContract.View.LoginView){
            this.mLoginView = (AccountContract.View.LoginView) view;
            this.mLoginView.setPresenter(this);
        }
        if(view instanceof AccountContract.View.ProfileView){
            this.mProfileView = (AccountContract.View.ProfileView) view;
            this.mProfileView.setPresenter(this);
        }
        if(view instanceof AccountContract.View.UpdateView){
            this.mUpdateView = (AccountContract.View.UpdateView) view;
            this.mUpdateView.setPresenter(this);
        }

        userObj = new User();
    }

    @Override
    public void start() {

    }

    @Override
    public void onCheckDataAccount() {
        userObj = ActivityUtils.getDataObject(context, new User().getClass());
        if (!(userObj == null)) {
            mAccountView.showProfileView();
        } else {
            mAccountView.showLoginView();
        }
    }

    @Override
    public void onLoginAccount(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Đang xủ lý...");
        progressDialog.show();
        if (username.equals("") || password.equals("")) {
            mLoginView.showLoginFailed("please fulfill information");
            progressDialog.dismiss();
        } else {
            List<User> userList = new ArrayList<>();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database;
            Log.d(TAG, "onLogin: đ vô data");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.d("onDataChange: ", data.getKey());
                        User user = data.getValue(User.class);
                        userList.add(user);
                    }
                    if (userList.size() > 0) {
                        Log.d("onDataChange: ", "data not null");
                        if (userList.get(0).getUserName().equals(user.getUserName().trim().toLowerCase())
                                && userList.get(0).getPassword().equals(user.getPassword().trim().toLowerCase())) {
                            Log.d("onDataChange: ", "data match");
                            ActivityUtils.setDataObject(context, userList.get(0));
                            mLoginView.showLoginSuccess();
                            EventBus.getDefault().post(userList.get(0));
                         //   ActivityUtils.restartAllFragmentDisplay((Activity) context);
                            progressDialog.dismiss();
                        } else {
                            Log.d("onDataChange: ", "data not match");
                            mLoginView.showLoginFailed("check your information");
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onUpdateAccount(User user) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Update...");
        progressDialog.setMessage("Đang xủ lý...");
        progressDialog.show();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.orderByChild("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child("User").setValue(user);
                ActivityUtils.setDataObject(context, user);
                EventBus.getDefault().post(user);
                mUpdateView.showUpdateSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mUpdateView.showUpdateFailed("Error");
            }
        });
    }

    @Override
    public void onLogoutAccount() {
        ActivityUtils.removeAllDataObject(context);
        EventBus.getDefault().post(userObj);
        if (context != null) {
            mProfileView.restartViewAccount();
          //  ActivityUtils.restartAllFragmentDisplay((Activity) context);
        }

    }

    @Override
    public void onGetProfile() {
        userObj = ActivityUtils.getDataObject(context, new User().getClass());
        mProfileView.showProfileAccount();
    }


}
