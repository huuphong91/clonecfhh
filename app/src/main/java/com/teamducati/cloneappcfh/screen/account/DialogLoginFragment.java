package com.teamducati.cloneappcfh.screen.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.User;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DialogLoginFragment extends DialogFragment {

    private static final String TAG = "DialogLogin";
    private Unbinder unbinder;

    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;

    private User userLogin;

    private static DialogLoginFragment sInstance;

    @Inject
    public DialogLoginFragment() {
        // Required empty public constructor
    }

    private static DialogLoginFragment destroyInstance() {
        if (sInstance != null) {
            sInstance = null;
        }
        return sInstance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RepickShipAddressFullScreen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLogin.setOnClickListener(v -> {
            String username = mEdtUsername.getText().toString();
            String password = mEdtPassword.getText().toString();
            userLogin = new User();
            userLogin.setUserName(username);
            userLogin.setPassword(password);
            checkUser(userLogin);
        });
    }

    private void checkUser(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        if (username.equals("") || password.equals("")) {
            Toast.makeText(getActivity(), "please fulfill information", Toast.LENGTH_SHORT).show();
        } else {
            List<User> userList = new ArrayList<>();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        User user = data.getValue(User.class);
                        userList.add(user);
                    }
                    if (userList.size() > 0) {
                        if (userList.get(0).getUserName().equals(user.getUserName().trim().toLowerCase())
                                && userList.get(0).getPassword().equals(user.getPassword().trim().toLowerCase())) {
//                            ActivityUtils.setDataObject(context, userList.get(0));
                            Intent userIntent = new Intent(Constants.ACTION_USER_RESULT);
                            userIntent.putExtra("User", userList.get(0));
                            LocalBroadcastManager.getInstance(getActivity())
                                    .sendBroadcast(userIntent);
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), "check your information", Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyInstance();
        Log.d(TAG, "onDestroy");
    }
}
