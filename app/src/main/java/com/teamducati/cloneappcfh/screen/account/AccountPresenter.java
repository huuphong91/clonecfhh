package com.teamducati.cloneappcfh.screen.account;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mAccountView;

    public AccountPresenter(AccountContract.View accountView) {
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
            List<User> users = new ArrayList<>();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database;
            Log.d(TAG, "onLogin: đ vô data");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.d("onDataChange: ", data.getKey());
                        User user = data.getValue(User.class);
                        users.add(user);
                    }
                    if (!users.isEmpty()) {
                        for (User user : users) {
                            if (user.getUserName() == null) {
                                mAccountView.showLoginFail("this username doesn't exist");
                            }
                            if (user.getUserName().equals(user.getUserName()) && user.getPassword().equals(user.getPassword())) {
                                {
                                    mAccountView.showUserDetail(user);
                                }
                            } else mAccountView.showLoginFail("check your information");

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
    public void onLogout(User user) {
        mAccountView.showLoginScreen();
    }

    @Override
    public void updateUserProperty(User user) {

    }
}
