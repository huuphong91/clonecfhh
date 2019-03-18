package com.teamducati.cloneappcfh.screen.account;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AccountPresenter implements AccountContract.Presenter {

    private Context context;

    private AccountContract.View mAccountView;

    public AccountPresenter(Context context, AccountContract.View accountView) {
        this.mAccountView = accountView;
        mAccountView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onLogin(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        if (username.equals("") || password.equals("")) {
            mAccountView.showLoginFail("please fulfill information");
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
                            mAccountView.showUserDetail(userList.get(0));
                            ActivityUtils.setDataObject(context, userList.get(0));
                        } else {
                            Log.d("onDataChange: ", "data not match");
                            mAccountView.showLoginFail("check your information");
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
    public void onLogout() {
        mAccountView.showLoginScreen();
        ActivityUtils.removeAllDataObject(context);
    }

    @Override
    public void updateUserProperty(User user) {

    }
}
