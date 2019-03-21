package com.teamducati.cloneappcfh.screen.account;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.ActivityUtils;
import com.teamducati.cloneappcfh.utils.eventsbus.EventBusStore;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AccountPresenter implements AccountContract.Presenter {

    private Context context;

    private AccountContract.View mAccountView;

    private User userObj;

    public AccountPresenter(Context context, AccountContract.View accountView) {
        this.context=context;
        this.mAccountView = accountView;
        mAccountView.setPresenter(this);
        userObj=new User();
    }

    @Override
    public void start() {
        userObj = ActivityUtils.getDataObject(context, userObj.getClass());
        if (!(userObj==null)) {
            mAccountView.showUserDetail();
        } else {
            mAccountView.showLoginScreen();
        }
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
                            ActivityUtils.setDataObject(context, userList.get(0));
                            EventBus.getDefault().post(userList.get(0));
                            mAccountView.showUserDetail();
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
        ActivityUtils.removeAllDataObject(context);
        mAccountView.restartViewAccount();

    }

    @Override
    public void updateUserProperty(User user) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.orderByChild("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child("User").setValue(user);
                ActivityUtils.setDataObject(context, user);
                EventBus.getDefault().post(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(context, "updated successful", Toast.LENGTH_SHORT).show();
    }
}
