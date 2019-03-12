package com.teamducati.cloneappcfh.screen.account;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
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
            DatabaseReference database = FirebaseDatabase.getInstance().getReference(Constants.TABLE_NAME);
            DatabaseReference myRef = database;
            Log.d(TAG, "onLogin: đ vô data");
            myRef.orderByChild(Constants.COLUMN_NAME).equalTo(username)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<User> users = new ArrayList<>();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Log.d("onDataChange: ", data.getKey());
                                User user = data.getValue(User.class);
                                users.add(user);
                                };
                            if (user.getUserName() == null) {
                                mAccountView.showLoginFail("this username doesn't exist");
                            }
                            if (user.getUserName() != null && !user.getPassword().equals(password)) {
                                mAccountView.showLoginFail("please check your password");
                            } else mAccountView.showUserDetail(user);

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
